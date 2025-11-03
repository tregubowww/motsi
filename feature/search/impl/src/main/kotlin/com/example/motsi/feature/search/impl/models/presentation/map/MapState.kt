package com.example.motsi.feature.search.impl.models.presentation.map

import org.osmdroid.util.GeoPoint

data class MapState(
    val currentGeoPoint: GeoPoint = GeoPoint(55.751244, 37.618423), // Москва по умолчанию
    val currentZoom: Double = 12.0,
    val currentRotation: Float = 0f,
    val isLocationLoading: Boolean = false,
    val moveToUserGeoPosition: Boolean = false,
    val userGeoPosition: GeoPoint? = null,
    val alpha: Float = 1f,
)