package com.example.motsi.feature.search.impl.models.presentation.screen

import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel

internal data class SearchScreenState (
    val loadingState: LoadingState<SearchScreenModel, MotsiError> = LoadingState.Idle,
    val screenState: ScreenState = ScreenState.MAP_AND_LIST
){
    enum class ScreenState{
        MAP,
        MAP_AND_LIST,
        LIST
    }
}