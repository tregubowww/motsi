package com.example.motsi.core.ui.models

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import com.example.motsi.core.common.models.domain.SnackbarType

data class DataSnackbar(
    override val message: String,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    val type: SnackbarType = SnackbarType.Default,
) : SnackbarVisuals