package com.example.motsi.core.ui.designsystem.mapwidget

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.designsystem.indicators.ProgressIndicatorCircular
import com.example.motsi.core.ui.designsystem.snackbar.showMotsiSnackbar
import com.example.motsi.core.ui.models.DataSnackbar
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.core.ui.utils.CollectEffect
import kotlinx.coroutines.flow.Flow
import org.osmdroid.views.MapView
import androidx.activity.compose.rememberLauncherForActivityResult


@Composable
fun MapWidget(
    state: MapWidgetState,
    actions: MapWidgetActions,
    snackbarHostState: SnackbarHostState,
    snackBarFlow: Flow<DataSnackbar>,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val mapView = rememberOsmdroidMap(
        context = context,
        backgroundColor = Tokens.Background.getColor().toArgb(),
        onCameraMoved = actions.onCameraMoved,
        onMapClick = actions.onMapClick
    )

    val lifecycleOwner = LocalLifecycleOwner.current
    val iconFactory = remember(context) {
        MapIconFactory(context)
    }
    CollectEffect(snackBarFlow) { effect ->
        snackbarHostState.showMotsiSnackbar(
            effect,
            onActionPerformed = {
                context.startActivity(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                )
            }
        )
    }

    DisposableEffect(lifecycleOwner, mapView) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            // освобождаем ресурсы osmdroid
            mapView.overlays.clear()
            mapView.onDetach()
        }
    }

    LaunchedEffect(state.moveToMobileGeoPosition) {
        if (state.moveToMobileGeoPosition && state.mobileGeoPosition != null) {
            mapView.updateUserLocationPlacemark(state.mobileGeoPosition)
            actions.onShowMobileLocation.invoke()
        }
    }

    LaunchedEffect(key1 =  state.markers, key2 = state.currentZoom) {
        mapView.updateMarkers(state.markers ,iconFactory, actions.onPointClick )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clipToBounds()
    ) {


        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { mapView },
            update = { view ->
                if (view.mapCenter != state.currentGeoPoint) {
                    view.controller.setCenter(state.currentGeoPoint)
                }

                if (view.zoomLevel.toDouble() != state.currentZoom) {
                    view.controller.setZoom(state.currentZoom)
                }

                if (view.mapOrientation != state.currentRotation) {
                    view.mapOrientation = state.currentRotation
                }
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Tokens.Background.getColor().copy(alpha = state.alpha)),
        )


        if (state.isMobileLocationLoading) {
            ProgressIndicatorCircular(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(16.dp)
        ) {

            if (state.showZoomButtons) {
                ZoomControlsButtons(
                    modifier = Modifier.padding(bottom = 16.dp),
                    mapView = mapView
                )
            }

            if (state.showMobileLocationButton) {
                LocationButton(
                    onLocationGranted = {
                        actions.onGetMobileLocationClick.invoke (context)
                    }
                )
            }
        }
    }
}

@Composable
fun LocationButton(
    onLocationGranted: () -> Unit
) {
    val context = LocalContext.current

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            val granted = result[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    result[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (granted) {
                onLocationGranted()
            }
        }

    Icon(
        modifier = Modifier
            .size(48.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp))
            .background(Tokens.Background.getColor(), RoundedCornerShape(12.dp))
            .clickable {
                val fineGranted = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

                val coarseGranted = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

                if (fineGranted || coarseGranted) {
                    onLocationGranted()
                } else {
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            }
            .padding(12.dp),
        painter = painterResource(R.drawable.ic_navigation_fill_24dp),
        contentDescription = null,
        tint = Tokens.IconPrimary.getColor()
    )
}

@Composable
fun ZoomControlsButtons(
    modifier: Modifier = Modifier,
    mapView: MapView
) {
    Column(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp),
            )
    ) {
        // Кнопка увеличения
        Icon(
            modifier = Modifier
                .size(48.dp)
                .clickable { mapView.controller.zoomIn() }
                .background(
                    color = Tokens.Background.getColor(),
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
                .padding(12.dp),
            painter = painterResource(R.drawable.ic_add_24dp),
            contentDescription = null,
            tint = Tokens.IconPrimary.getColor()
        )

        // Кнопка уменьшения
        Icon(
            modifier = Modifier
                .size(48.dp)
                .clickable { mapView.controller.zoomOut() }
                .background(
                    color = Tokens.Background.getColor(),
                    shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                )
                .padding(12.dp),
            painter = painterResource(R.drawable.ic_minus_24dp),
            contentDescription = null,
            tint = Tokens.IconPrimary.getColor()
        )
    }
}

@Composable
private fun rememberOsmdroidMap(
    context: Context,
    backgroundColor: Int,
    onCameraMoved: (Double, Double, Double, Float) -> Unit,
    onMapClick: () -> Unit
): MapView = remember {
    getMapView(context, onCameraMoved, onMapClick, backgroundColor)
}
