package com.example.motsi.feature.search.impl.presentation.compose.mapwidget

import android.content.Context
import android.view.MotionEvent
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
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay

internal fun getMapView(
    context: Context,
    onChangeGeoPoint: (Double, Double, Double, Float) -> Unit,
    onMapClick: () -> Unit,
    colorBackground: Int
): MapView =
    MapView(context).apply {
        setUseDataConnection(true)
        setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        setMultiTouchControls(true)
        setBackgroundColor(colorBackground)
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
        //Клик по карте
        overlays.add(object : Overlay() {
            override fun onSingleTapConfirmed(event: MotionEvent?, mapView: MapView?): Boolean {
                event?.let { onMapClick() }
                return true
            }
        })

    }


internal fun MapView.updateUserLocationPlacemark(
    userLocationPlacemark: GeoPoint?,
) {
    if (!isAttachedToWindowCompat()) return

    overlays.removeAll { it is Marker && it.id == MarkerTypeId.USER_LOCATION.name }

    userLocationPlacemark?.let { userGeoPoint ->
        createAndAddMarker(userGeoPoint)
        controller?.animateTo(userGeoPoint, 17.0, 500L)
        post { mapOrientation = 0f }
    }
}

internal fun MapView.createAndAddMarker(geoPoint: GeoPoint) {
    val marker = Marker(this).apply {
        position = geoPoint
        icon = createUserLocationBitmap().toDrawable(context.resources)
        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        id = MarkerTypeId.USER_LOCATION.name
    }
    overlays.add(marker)
    invalidate()
}

internal fun MapView.updateActivityMarkers(
    listActivityState: SearchSportActivityListModel,
    context: Context
) {
    if (!isAttachedToWindowCompat()) return

    val userLocationMarkers =
        overlays.filter { it is Marker && it.id == MarkerTypeId.USER_LOCATION.name }
    overlays.removeAll { it is Marker }

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
        overlays.add(marker)
    }

    overlays.addAll(userLocationMarkers)
    invalidate()
}

private fun MapView.isAttachedToWindowCompat(): Boolean {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
        this.isAttachedToWindow
    } else {
        this.windowToken != null
    }
}

private enum class MarkerTypeId {
    USER_LOCATION,
    SPORT_ACTIVITY
}