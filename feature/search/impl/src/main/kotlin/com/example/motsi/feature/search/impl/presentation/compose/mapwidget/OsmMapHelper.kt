package com.example.motsi.feature.search.impl.presentation.compose.mapwidget

import android.content.Context
import com.example.motsi.core.ui.utils.createUserLocationBitmap
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import androidx.core.graphics.drawable.toDrawable
import com.example.motsi.core.ui.theming.AppResources
import com.example.motsi.core.ui.utils.convertSportActivityIconToBitmap
import com.example.motsi.feature.search.impl.models.domain.SearchSportActivityListModel
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay

internal fun getMapView(
    context: Context,
    onChangeGeoPoint: (Double, Double, Double, Float) -> Unit
): MapView =
    MapView(context).apply {
        setUseDataConnection(true)
        setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)// Вид карты
        setMultiTouchControls(true)
        minZoomLevel = 3.0
        maxZoomLevel = 20.0
        zoomController.setVisibility(
            CustomZoomButtonsController.Visibility.NEVER
        )
        overlays.add(RotationGestureOverlay(this).apply { isEnabled = true })
        addMapListener(object : MapListener {
            override fun onScroll(event: ScrollEvent?): Boolean {
                onChangeGeoPoint(
                    mapCenter.latitude,
                    mapCenter.longitude,
                    zoomLevelDouble,
                    mapOrientation
                )
                return false
            }

            override fun onZoom(event: ZoomEvent?): Boolean {
                onChangeGeoPoint(
                    mapCenter.latitude,
                    mapCenter.longitude,
                    zoomLevelDouble,
                    mapOrientation
                )
                return false
            }
        })
    }

internal fun MapView.updateUserLocationPlacemark(
    userLocationPlacemark: GeoPoint?,
) {
    // Удаляем старый маркер геопозиции пользователя
    this.overlays.removeAll {
        it is Marker && it.id == MarkerTypeId.USER_LOCATION.name
    }

    userLocationPlacemark?.let { userGeoPoint ->
        val marker = Marker(this).apply {
            position = userGeoPoint
            icon = createUserLocationBitmap().toDrawable(context.resources)
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            id = MarkerTypeId.USER_LOCATION.name
        }

        overlays.add(marker)

        controller?.animateTo(userGeoPoint, 17.0, 500L)
        post { mapOrientation = 0f }

        invalidate()
    }
}

internal fun MapView.updateActivityMarkers(
    listActivityState: SearchSportActivityListModel,
    context: Context
) {
    // Сохраняем маркер геопозиции пользователя
    val userLocationMarkers =
        overlays.filter { it is Marker && it.id == MarkerTypeId.USER_LOCATION.name }

    // Удаляем ВСЕ маркеры
    this.overlays.removeAll { it is Marker }

    listActivityState.sportActivityList.forEach { markerData ->
        val marker = Marker(this).apply {
            position = GeoPoint(
                markerData.mapData.locationPoint.first,
                markerData.mapData.locationPoint.second
            )
            icon = convertSportActivityIconToBitmap(
                context = context,
                iconActivity = AppResources.iconRes(markerData.mapData.iconMark)
            ).toDrawable(context.resources)
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            id = MarkerTypeId.SPORT_ACTIVITY.name
        }

        // Сперка рисуются маркеры активностей
        overlays.add(marker)


    }
    // Отрисовка маркера геопозиции пользователя поверх всех остальных меток
    overlays.addAll(userLocationMarkers)

    invalidate()
    postInvalidateDelayed(16)
}

private enum class MarkerTypeId {
    USER_LOCATION,
    SPORT_ACTIVITY
}