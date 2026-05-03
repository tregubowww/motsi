package com.example.motsi.feature.search.impl.models.presentation.map

import android.content.Context

internal sealed class SearchMapIntent {
    data class OnGetMobileLocationClick(val context: Context) :
        SearchMapIntent()

    data class OnCameraMoved(
        val latitude: Double,
        val longitude: Double,
        val zoom: Double,
        val rotation: Float
    ) :
        SearchMapIntent()

    data object OnShowMobileGeoPosition : SearchMapIntent()
    data class UpdateAlpha(val alpha: Float) : SearchMapIntent()
    data class OnPointClick(val id: String) : SearchMapIntent()
}

