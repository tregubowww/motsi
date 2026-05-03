package com.example.motsi.core.ui.designsystem.mapwidget

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import com.example.motsi.core.ui.R
import org.osmdroid.views.overlay.Marker

internal fun getMapView(
    context: Context,
    onCameraMoved: (Double, Double, Double, Float) -> Unit,
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
                onCameraMoved(
                    mapCenter.latitude,
                    mapCenter.longitude,
                    zoomLevelDouble,
                    mapOrientation
                )
                return false
            }

            override fun onZoom(event: ZoomEvent?): Boolean {
                onCameraMoved(
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


internal fun MapView.updateUserLocationPlacemark(point: GeoPoint?) {
    if (!isAttachedToWindowCompat()) return

    if (point == null) {
        removeUserLocationMarker()
        return
    }

    val marker = userLocationMarker ?: createUserLocationMarker(point).also {
        userLocationMarker = it
        overlays.add(it)
    }

    if (marker.position != point) {
        marker.position = point
    }

    controller?.animateTo(point, 17.0, 500L)
    post { mapOrientation = 0f }

    invalidate()
}

private fun MapView.createUserLocationMarker(geoPoint: GeoPoint): Marker {
    return org.osmdroid.views.overlay.Marker(this).apply {
        id = MarkerTypeId.USER_LOCATION.name
        position = geoPoint
        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        icon = userLocationIcon
    }
}

private fun MapView.removeUserLocationMarker() {
    userLocationMarker?.let {
        overlays.remove(it)
        userLocationMarker = null
        invalidate()
    }
}

private var MapView.userLocationMarker: Marker?
    get() = getTag(R.id.user_location_marker_tag) as? Marker
    set(value) = setTag(R.id.user_location_marker_tag, value)

private val MapView.userLocationIcon: Drawable
    get() {
        val tag = R.id.user_location_icon_tag
        val cached = getTag(tag) as? Drawable
        if (cached != null) return cached

        val icon = requireNotNull(
            ContextCompat.getDrawable(context, R.drawable.ic_user_location)
        ).mutate()

        setTag(tag, icon)
        return icon
    }

internal fun MapView.updateMarkers(
    data: List<MapWidgetState.Marker>,
    mapIconFactory: MapIconFactory,
    omPointClick: (String) -> Unit
) {
    if (!isAttachedToWindowCompat()) return

    val newIds = data.map { it.id }.toSet()

    // удалить старые
    val iterator = markersCache.iterator()
    while (iterator.hasNext()) {
        val entry = iterator.next()
        if (entry.key !in newIds) {
            overlays.remove(entry.value)
            iterator.remove()
        }
    }

    data.forEach { point ->
        val marker = markersCache[point.id] ?: Marker(this).also {
            it.id = point.id
            overlays.add(it)
            markersCache[point.id] = it
            it.setOnMarkerClickListener { marker: Marker, mapView: MapView ->
                omPointClick.invoke(point.id)
                val projection = mapView.projection
                val screenPoint = projection.toPixels(
                    marker.position,
                    null
                )

                val offsetY = (mapView.height * 0.2).toInt()
                screenPoint.y += offsetY

                val adjustedCenter = projection.fromPixels(screenPoint.x, screenPoint.y) as GeoPoint
                mapView.controller.animateTo(adjustedCenter)
                true
            }
        }

        marker.position = GeoPoint(point.latitude, point.longitude)

        when (point.type) {
            MapWidgetState.Marker.Type.Dot -> {
                marker.icon = AppCompatResources.getDrawable(
                    context,
                    R.drawable.ic_map_dot_marker_36
                )
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)

            }

            MapWidgetState.Marker.Type.SelectedMarker -> {
                marker.icon = AppCompatResources.getDrawable(
                    context,
                    R.drawable.ic_map_marker_36
                )
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            }

            MapWidgetState.Marker.Type.MarkerText -> {
                val prevText = marker.relatedObject as? String

                if (prevText != point.description) {
                    marker.icon = mapIconFactory.createMarkerWithText(point.description)
                    marker.relatedObject = point.description
                }
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            }
        }
    }

    invalidate()
}

private val MapView.markersCache: MutableMap<String, Marker>
    get() {
        val tag = R.id.markers_cache_tag
        return (getTag(tag) as? MutableMap<String, Marker>)
            ?: mutableMapOf<String, Marker>().also { setTag(tag, it) }
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
}
