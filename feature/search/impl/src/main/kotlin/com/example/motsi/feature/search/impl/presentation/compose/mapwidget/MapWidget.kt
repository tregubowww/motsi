package com.example.motsi.feature.search.impl.presentation.compose.mapwidget

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.ui.designsystem.indicators.ProgressIndicatorCircular
import com.example.motsi.core.ui.theming.Body3Primary
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel
import com.example.motsi.feature.search.impl.models.presentation.map.MapState
import com.example.motsi.feature.search.impl.models.presentation.map.SearchMapIntent
import com.example.motsi.feature.search.impl.presentation.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import org.osmdroid.config.Configuration.getInstance
import org.osmdroid.views.MapView

@Composable
internal fun MapWidget(
    viewModel: SearchViewModel,
    snackbarHostState: SnackbarHostState,
    screenModel: SearchScreenModel,
    modifier: Modifier = Modifier,
) {
    val listActivityState by viewModel.listActivityState.collectAsState()
    val mapState by viewModel.mapState.collectAsState()
    val context = LocalContext.current

    val onGeoPointChange by rememberUpdatedState { lat: Double, lon: Double, zoom: Double, rotation: Float ->
        viewModel.onMapIntent(SearchMapIntent.ChangeGeoPoint(lat, lon, zoom, rotation))
    }
    val onMapClick by rememberUpdatedState { viewModel.onMapIntent(SearchMapIntent.ShowMap) }

    val mapView = remember { getMapView(context, onGeoPointChange, onMapClick) }

    LaunchedEffect(Unit) {
        getInstance().load(
            context,
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        )
    }

    Map(
        modifier = modifier,
        screenModel = screenModel,
        viewModel = viewModel,
        mapState = mapState,
        mapView = mapView,
        context = context,
        snackbarHostState = snackbarHostState
    )

    LaunchedEffect(Unit) {
        snapshotFlow { mapState.moveToUserGeoPosition }
            .collectLatest { shouldMove ->
                if (shouldMove && mapState.userGeoPosition != null) {
                    mapView.updateUserLocationPlacemark(mapState.userGeoPosition)
                    viewModel.onMapIntent(SearchMapIntent.OnShowUserGeoposition)
                }
            }
    }

    LaunchedEffect(mapState.dataSnackbar) {
        mapState.dataSnackbar?.let { dataSnackBar ->
            snackbarHostState.showSnackbar(dataSnackBar)
        }
    }

    when (val listState = listActivityState.loadingState) {
        is LoadingState.Success -> {
            LaunchedEffect(listState.data.sportActivityList) {
                mapView.updateActivityMarkers(
                    listActivityState = listState.data,
                    context = context
                )
            }
        }

        else -> Unit // Остальные состояния игнорируем пока
    }
}


@Composable
private fun Map(
    screenModel: SearchScreenModel,
    viewModel: SearchViewModel,
    mapState: MapState,
    mapView: MapView,
    context: Context,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier,
) {
    Box(modifier = modifier) {

        AndroidView(
            factory = { mapView },
            update = {
                it.controller.setCenter(mapState.currentGeoPoint)
                it.controller.setZoom(mapState.currentZoom)
                it.mapOrientation = mapState.currentRotation
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

        if (mapState.isMapOpen) {
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
                        viewModel.onMapIntent(SearchMapIntent.OnLocationClick(context))
                    }
                )
            }

            ShowListButton(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart),
                text = screenModel.buttonTextForListOpen.orEmpty(),
                onClick = {
                    viewModel.onMapIntent(SearchMapIntent.HideMap)
                }
            )
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
fun ShowListButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
            )
            .clickable { onClick() }
            .background(
                color = Tokens.Background.getColor(),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)

    ) {
        Icon(
            painter = painterResource(R.drawable.ic_view_list_24dp),
            contentDescription = null,
            tint = Tokens.IconPrimary.getColor()
        )

        Body3Primary(
            modifier = Modifier.padding(start = 8.dp),
            text = text
        )
    }
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