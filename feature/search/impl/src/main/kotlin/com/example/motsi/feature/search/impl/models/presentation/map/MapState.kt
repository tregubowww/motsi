package com.example.motsi.feature.search.impl.models.presentation.map

import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition

internal data class MapState(
    var cameraPositionPoint: Point = Point(0.0, 0.0),
    var cameraPositionZoom: Float = 0.0f,
    var cameraPositionAzimuth: Float = 0.0f,
    var cameraPositionTilt: Float = 0.0f,
    var isMapInitialized: Boolean = false,
    var isLocationLoading: Boolean = false,
    var isMapOpen: Boolean = false,
    var snackbarData: DataSnackbar? = null,
    var userLocationPlacemark: Point?  = null,
    var alpha: Float = 1f,
)