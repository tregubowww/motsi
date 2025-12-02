package com.example.motsi.feature.mysportactivities.impl.models.presentation

import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.mysportactivities.impl.models.domain.MySportActivitiesModel

internal data class MySportActivitiesScreenState (
    val loadingState: LoadingState<MySportActivitiesModel, MotsiError> = LoadingState.Idle,
)