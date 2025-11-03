package com.example.motsi.feature.search.impl.models.presentation.map

import android.content.Context

internal sealed class SearchMapIntent {
    data class OnLocationClick(val context: Context) :
        SearchMapIntent()

    data class ChangeGeoPoint(
        val latitude: Double,
        val longitude: Double,
        val zoom: Double,
        val rotation: Float
    ) :
        SearchMapIntent()

    data object OnShowUserGeoposition : SearchMapIntent()
    data class UpdateAlpha(val alpha: Float) : SearchMapIntent()
}

