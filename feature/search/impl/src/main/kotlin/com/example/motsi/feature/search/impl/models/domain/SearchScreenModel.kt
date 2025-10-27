package com.example.motsi.feature.search.impl.models.domain

internal data class SearchScreenModel(
    val defaultSearchHint: String,
    val buttonTextForListOpen: String?,
    val dataSnackbarText: DataSnackbarText
){
    data class DataSnackbarText(
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
}