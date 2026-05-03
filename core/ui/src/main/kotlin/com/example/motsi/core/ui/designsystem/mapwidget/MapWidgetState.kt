package com.example.motsi.core.ui.designsystem.mapwidget

import com.example.motsi.core.ui.models.DataSnackbar
import org.osmdroid.util.GeoPoint

data class MapWidgetState(
    val currentGeoPoint: GeoPoint? = null,
    val currentZoom: Double = 12.0,
    val currentRotation: Float = 0f,
    val isMobileLocationLoading: Boolean = false,
    val moveToMobileGeoPosition: Boolean = false,
    val mobileGeoPosition: GeoPoint? = null,
    val markers: List<Marker> = emptyList(),
    val showMobileLocationButton: Boolean = true,
    val showZoomButtons: Boolean = true,
    val dataSnackBar: DataSnackbar? = null,
    val alpha: Float = 1f,
) {
    data class Marker(
        val id: String,
        val latitude: Double,
        val longitude: Double,
        val type: Type,
        val description: String,
    ) {
        enum class Type {
            Dot,
            SelectedMarker,
            MarkerText
        }
    }
}
