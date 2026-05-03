package com.example.motsi.core.ui.designsystem.mapwidget

data class MapWidgetDataSnackbar(
    val dataSnackbarPermission: DataSnackbar,
    val dataSnackbarInternet: DataSnackbar,
    val dataSnackbarLocation: DataSnackbar,
    val dataSnackbarLoadingLocation: DataSnackbar,
) {
    data class DataSnackbar(
        val message: String,
        val actionLabel: String? = null
    )
}