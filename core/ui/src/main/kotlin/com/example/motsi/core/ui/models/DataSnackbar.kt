package com.example.motsi.core.ui.models

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

data class DataSnackbar(
    val id: Long = System.currentTimeMillis(),
    override val message: String,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    val type: SnackbarType = SnackbarType.Default,
) : SnackbarVisuals{
    sealed interface SnackbarType {
        data object Default : SnackbarType
        data object Action : SnackbarType
        data object Success : SnackbarType
        data object Error : SnackbarType
    }
}