package com.example.motsi.feature.search.impl.models.presentation.tips

import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.models.domain.SearchTipsListModel

internal data class SearchTipListState (
    val loadingState: LoadingState<SearchTipsListModel, MotsiError> = LoadingState.Idle,
)