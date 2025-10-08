package com.example.motsi.feature.addsportactivity.impl.models.presentation

import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.addsportactivity.impl.models.domain.ChoiceTypeSportModel
import com.example.motsi.feature.addsportactivity.impl.models.domain.ChoiceTypeSportModel.TypeSport
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class ChoiceTypeSportState(
    val loadingState: LoadingState<ChoiceTypeSportModel, MotsiError> = LoadingState.Idle,
    val listTypeSport: ImmutableList<TypeSport> = persistentListOf(),
)