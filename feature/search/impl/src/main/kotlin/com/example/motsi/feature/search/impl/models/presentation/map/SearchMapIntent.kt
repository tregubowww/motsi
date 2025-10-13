package com.example.motsi.feature.search.impl.models.presentation.map

import android.content.Context
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import com.yandex.mapkit.map.CameraPosition

internal sealed class SearchMapIntent {
    data class ShowSnackbar(val dataSnackBar: DataSnackbar, val context: Context) :
        SearchMapIntent()
    data class OnLocationClick(val context: Context) :
        SearchMapIntent()
    data class UpdateCameraPosition(val cameraPosition: CameraPosition) : SearchMapIntent()
    data object ShowMap : SearchMapIntent()
    data object HideMap : SearchMapIntent()
    data class UpdateAlpha(val alpha: Float) : SearchMapIntent()
    data class UpdateZoom(val zoom: Float) : SearchMapIntent()
    data class OnClickSnackbarAction(
        val context: Context,
        val result: SnackbarResult?
    ) : SearchMapIntent()
}

data class DataSnackbar(
    val message: String,
    val duration: SnackbarDuration,
    val actionLabel: String? = null,
    val type: SnackbarType = SnackbarType.Default
) {
    sealed class SnackbarType {
        data object Default : SnackbarType()
        data object ActionMessage : SnackbarType()
    }
}