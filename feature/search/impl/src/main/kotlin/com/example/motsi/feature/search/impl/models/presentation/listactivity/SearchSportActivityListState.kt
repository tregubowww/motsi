package com.example.motsi.feature.search.impl.models.presentation.listactivity

import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.models.domain.SearchSportActivityListModel

internal data class SearchSportActivityListState (
    val loadingState: LoadingState<SearchSportActivityListModel, MotsiError> = LoadingState.Idle,
//    val listModel: SearchListActivityModel = SearchListActivityModel(),
//    val isLoading: Boolean = false,
//    val error: MotsiError? = null
)