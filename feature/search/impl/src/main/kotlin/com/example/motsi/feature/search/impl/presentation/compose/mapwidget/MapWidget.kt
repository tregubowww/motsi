package com.example.motsi.feature.search.impl.presentation.compose.mapwidget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.theming.Tokens
import com.yandex.mapkit.mapview.MapView
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.CustomSnackbarHost
import com.example.motsi.core.ui.designsystem.indicators.ProgressIndicatorCircular
import com.example.motsi.core.ui.theming.Body3Primary
import com.example.motsi.core.ui.utils.LifecycleEffect
import com.example.motsi.feature.search.impl.models.presentation.map.SearchMapIntent
import com.example.motsi.feature.search.impl.presentation.SearchViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import kotlinx.coroutines.delay

@Composable
internal fun MapWidget(
    viewModel: SearchViewModel,
    snackbarHostState: SnackbarHostState
) {
    val mapState by viewModel.mapState.collectAsState()
    val context = LocalContext.current
    val listActivityState by viewModel.listActivityState.collectAsState()
    val mapView = remember { MapView(context) }
    val userPlacemark = remember { mapView.mapWindow.map.mapObjects.addCollection() }
    val sportActivityPlacemarks = remember { mapView.mapWindow.map.mapObjects.addCollection() }
    val zoomCityCameraPosition = 14f

    // Управление жизненным циклом
    LifecycleEffect(
        onStart = {
            MapKitFactory.getInstance().onStart()
            mapView.onStart()
        },
        onStop = {
            mapView.onStop()
            MapKitFactory.getInstance().onStop()
        }
    )

    when (val listState = listActivityState.loadingState) {
        is LoadingState.Loading -> {
            //Шимеризация
        }

        is LoadingState.Success -> {
            //Инициализация карты
            if (!mapState.isMapInitialized) {
                mapView.mapWindow.map.move(
                    CameraPosition(
                        Point(
                            listState.data.cityLocation.first,
                            listState.data.cityLocation.second
                        ),
                        zoomCityCameraPosition,
                        mapState.cameraPositionAzimuth,//Должно быть 0f
                        mapState.cameraPositionTilt//Должно быть 0f
                    )
                )
                mapState.isMapInitialized = true
            } else {
                mapView.mapWindow.map.move(
                    CameraPosition(
                        mapState.cameraPositionPoint,
                        mapState.cameraPositionZoom,
                        mapState.cameraPositionAzimuth,
                        mapState.cameraPositionTilt
                    )
                )
            }

            // Сохраняем текущую позицию в состояние
            LaunchedEffect(mapState.cameraPositionPoint) {
                viewModel.onMapIntent(
                    SearchMapIntent.UpdateCameraPosition(mapView.mapWindow.map.cameraPosition)
                )
                //    TODO Тут происходит бесконечный цикл. Он блокирует интенты
            }

            //Показ снекбаров
            LaunchedEffect(mapState.snackbarData) {
                mapState.snackbarData?.let { dataSnackBar ->
                    snackbarHostState.showSnackbar(
                        message = dataSnackBar.message,
                        actionLabel = dataSnackBar.actionLabel,
                        duration = dataSnackBar.duration
                    )
                }
                mapState.snackbarData = null
            }


            // Обновление метки геопозиции пользователя
            LaunchedEffect(mapState.userLocationPlacemark) {
                userPlacemark.updateUserLocationPlacemark(
                    userLocationPlacemark = mapState.userLocationPlacemark,
                    mapView = mapView
                )
            }

            // Создание меток активностей
            LaunchedEffect(listState.data.sportActivityList) {
                sportActivityPlacemarks.updateActivityMarkers(
                    listActivityState = listState.data,
                    context = context
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                AndroidView(
                    factory = { mapView }
                )

                // Индикатор загрузки геолокации
                if (mapState.isLocationLoading) {
                    ProgressIndicatorCircular(
                        modifier = Modifier
                            .align(alignment = Alignment.Center)
                    )
                }

                if (!mapState.isMapOpen) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                viewModel.onMapIntent(SearchMapIntent.ShowMap)
                            }
                            .background(color = Tokens.Background.getColor().copy(mapState.alpha))
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(16.dp)
                    ) {
                        ZoomControlsButtons(
                            modifier = Modifier
                                .padding(bottom = 16.dp),
                            mapView = mapView
//                            onClickPlus = {
//                                viewModel.onMapIntent(SearchMapIntent
//                                    .UpdateZoom(mapState.cameraPositionZoom + 1f))
//                            },
//                            onClickMinus = {
//                                viewModel.onMapIntent(SearchMapIntent
//                                    .UpdateZoom(mapState.cameraPositionZoom - 1f))
//                            }
                        )
                        LocationButton(
                            takeLocation = {
                                viewModel.onMapIntent(
                                    SearchMapIntent.OnLocationClick(
                                        context
                                    )
                                )
                            }
                        )
                    }

                    ShowListButton(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomStart),
                        text = listState.data.buttonTextForListOpen.orEmpty(),
                        onClick = { viewModel.onMapIntent(SearchMapIntent.HideMap) }
                    )
                }

                CustomSnackbarHost(
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    onActionClick = {
                        viewModel.onMapIntent(
                            SearchMapIntent.OnClickSnackbarAction(
                                context = context,
                                result = SnackbarResult.ActionPerformed
                            )
                        )
                    }
                )
            }
        }

        is LoadingState.Error -> {

        }

        else -> {
            //nothing
        }
    }
}


@Composable
private fun LocationButton(takeLocation: () -> Unit) {
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
            .clickable { takeLocation() }
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
            painter = painterResource(R.drawable.ic_view_list_outline_24dp),
            contentDescription = null,
            tint = Tokens.IconPrimary.getColor()
        )

        Spacer(modifier = Modifier.width(8.dp))

        Body3Primary(
            text = text
        )
    }
}

@Composable
fun ZoomControlsButtons(
    modifier: Modifier = Modifier,
    mapView: MapView
//    onClickPlus: () -> Unit,
//    onClickMinus: () -> Unit
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
                .clickable { mapView.zoomPlus() }//onClickPlus()
                .background(
                    color = Tokens.Background.getColor(),
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
                .padding(12.dp),
            painter = painterResource(R.drawable.ic_add_outline_24dp),
            contentDescription = null,
            tint = Tokens.IconPrimary.getColor()
        )

        // Кнопка уменьшения
        Icon(
            modifier = Modifier
                .size(48.dp)
                .clickable { mapView.zoomMinus() }//onClickMinus()
                .background(
                    color = Tokens.Background.getColor(),
                    shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                )
                .padding(12.dp),
            painter = painterResource(R.drawable.ic_minus_outline_24dp),
            contentDescription = null,
            tint = Tokens.IconPrimary.getColor()
        )
    }
}