package com.example.motsi.core.common.models.domain

sealed interface SnackbarType {
    data object Default : SnackbarType
    data object Action : SnackbarType
    data object Success : SnackbarType
    data object Error : SnackbarType
}
