package com.example.motsi.feature.userprofile.impl.models.presentation

import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.userprofile.impl.models.domain.UserProfileScreenModel

internal data class UserProfileScreenState (
    val loadingState: LoadingState<UserProfileScreenModel, MotsiError> = LoadingState.Idle,
)
