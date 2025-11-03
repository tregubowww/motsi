package com.example.motsi.feature.search.impl.presentation.compose.mapwidget

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.ui.designsystem.buttons.IconTextButton
import com.example.motsi.core.ui.designsystem.indicators.ProgressIndicatorCircular
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel
import com.example.motsi.feature.search.impl.models.presentation.SearchIntent
import com.example.motsi.feature.search.impl.models.presentation.map.MapState
import com.example.motsi.feature.search.impl.models.presentation.map.SearchMapIntent
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenIntent
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenState
import com.example.motsi.feature.search.impl.presentation.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import org.osmdroid.config.Configuration.getInstance
import org.osmdroid.views.MapView

@Composable
internal fun MapWidget(
    viewModel: SearchViewModel,
    modifier: Modifier = Modifier,
    showWidgetFullScreen: () -> Unit,
) {
    val listActivityState by viewModel.listActivityState.collectAsState()
    val mapState by viewModel.mapState.collectAsState()

    val context = LocalContext.current

    val mapView = rememberMapViewWithLifecycle(
        context = context,
        onGeoPointChange = { lat, lon, zoom, rotation ->
            viewModel.dispatch(
                SearchIntent.Map(
                    SearchMapIntent.ChangeGeoPoint(
                        lat,
                        lon,
                        zoom,
                        rotation
                    )
                )
            )
        },
        onMapClick = showWidgetFullScreen,
        backgroundColor = Tokens.Background.getColor().toArgb()
    )

    LaunchedEffect(context.applicationContext) {
        getInstance().load(
            context,
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        )
    }

    Map(
        modifier = modifier,
        viewModel = viewModel,
        mapState = mapState,
        mapView = mapView,
        context = context,
    )

    LaunchedEffect(mapState.moveToUserGeoPosition) {
        if (mapState.moveToUserGeoPosition && mapState.userGeoPosition != null) {
            mapView.updateUserLocationPlacemark(mapState.userGeoPosition)
            viewModel.dispatch(SearchIntent.Map(SearchMapIntent.OnShowUserGeoposition))
        }
    }

    when (val listState = listActivityState.loadingState) {
        is LoadingState.Success -> {
            LaunchedEffect(listState.data.sportActivityList) {
                mapView.updateActivityMarkers(listState.data, context)
            }
        }

        else -> Unit
    }
}


@Composable
private fun Map(
    viewModel: SearchViewModel,
    mapState: MapState,
    mapView: MapView,
    context: Context,
    modifier: Modifier,
) {
    val screenState by viewModel.screenState.collectAsState()
    Box(modifier = modifier) {

        AndroidView(
            factory = { mapView },
            update = { view ->
                val shouldUpdateCenter = view.mapCenter != mapState.currentGeoPoint
                val shouldUpdateZoom = view.zoomLevel.toDouble() != mapState.currentZoom

                if (shouldUpdateCenter || shouldUpdateZoom) {
                    view.controller.setCenter(mapState.currentGeoPoint)
                    view.controller.setZoom(mapState.currentZoom)
                }

                if (view.mapOrientation != mapState.currentRotation) {
                    view.mapOrientation = mapState.currentRotation
                }
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Tokens.Background.getColor().copy(alpha = mapState.alpha)),
        )

        if (mapState.isLocationLoading) {
            ProgressIndicatorCircular(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (screenState.screenState == SearchScreenState.ScreenState.MAP) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
            ) {
                ZoomControlsButtons(
                    modifier = Modifier.padding(bottom = 16.dp),
                    mapView = mapView
                )

                LocationButton(
                    onClick = {
                        viewModel.dispatch(
                            SearchIntent.Map(
                                SearchMapIntent.OnLocationClick(context)
                            )
                        )
                    }
                )

            }
        }
    }
}

@Composable
private fun LocationButton(onClick: () -> Unit) {
    Icon(
        modifier = Modifier
            .size(48.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp),
            )
            .background(
                color = Tokens.Background.getColor(),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
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
fun rememberMapViewWithLifecycle(
    context: Context,
    onGeoPointChange: (Double, Double, Double, Float) -> Unit,
    onMapClick: () -> Unit,
    backgroundColor: Int
): MapView {
    val mapView = remember(context) {
        getMapView(context, onGeoPointChange, onMapClick, backgroundColor)
    }

    val lifecycleOwner = LocalLifecycleOwner.current

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

    return mapView
}