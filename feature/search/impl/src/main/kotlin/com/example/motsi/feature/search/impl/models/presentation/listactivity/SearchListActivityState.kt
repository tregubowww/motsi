package com.example.motsi.feature.search.impl.models.presentation.listactivity

import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.models.domain.SearchListActivityModel

internal data class SearchListActivityState (
    val loadingState: LoadingState<SearchListActivityModel, MotsiError> = LoadingState.Idle,
//    val listModel: SearchListActivityModel = SearchListActivityModel(),
//    val isLoading: Boolean = false,
//    val error: MotsiError? = null
)