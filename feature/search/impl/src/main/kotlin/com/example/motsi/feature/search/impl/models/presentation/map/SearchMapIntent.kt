package com.example.motsi.feature.search.impl.models.presentation.map

import android.content.Context
import androidx.compose.material3.SnackbarResult
import com.example.motsi.core.ui.models.DataSnackbar

internal sealed class SearchMapIntent {
    data class ShowSnackbar(val dataSnackBar: DataSnackbar) :
        SearchMapIntent()

    data class OnLocationClick(val context: Context) :
        SearchMapIntent()

    data class ChangeGeoPoint(
        val latitude: Double,
        val longitude: Double,
        val zoom: Double,
        val rotation: Float
    ) :
        SearchMapIntent()

    data object ShowMap : SearchMapIntent()
    data object OnShowUserGeoposition : SearchMapIntent()
    data object HideMap : SearchMapIntent()
    data class UpdateAlpha(val alpha: Float) : SearchMapIntent()
}

