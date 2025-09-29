package com.example.motsi.impl.models.presentation

import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.impl.models.domain.SportActivityDetailsScreenModel

internal data class SportActivityDetailsScreenState (
    val loadingState: LoadingState<SportActivityDetailsScreenModel, MotsiError> = LoadingState.Idle,
    val isFavorites: Boolean = false
)