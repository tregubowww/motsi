package com.example.motsi.feature.search.impl.presentation

import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.utils.handleState
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.di.SearchHolder
import com.example.motsi.feature.search.impl.domain.interactor.SearchInteractor
import com.example.motsi.feature.search.impl.models.domain.SearchListDomainModel
import com.example.motsi.feature.search.impl.models.domain.SearchScreenDomainModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SearchViewModel @Inject constructor(
    private val interactor: SearchInteractor
) : BaseViewModel() {

    /** Состояние загрузки экрана */
    val loadingScreenState: StateFlow<LoadingState<SearchScreenDomainModel, MotsiError>> get() = _loadingScreenState.asStateFlow()
    private val _loadingScreenState =
        MutableStateFlow<LoadingState<SearchScreenDomainModel, MotsiError>>(LoadingState.Loading)

    /** Состояние загрузки экрана */
    val loadingSearchListState: StateFlow<LoadingState<SearchListDomainModel, MotsiError>> get() = _loadingSearchListState.asStateFlow()
    private val _loadingSearchListState =
        MutableStateFlow<LoadingState<SearchListDomainModel, MotsiError>>(LoadingState.Loading)

    /**
     * Проинициализировать вьюмодель
     */
    override fun onInit() {
        viewModelScope.launch {
            launch { interactor.getSearchScreen().handleState(_loadingScreenState) }
            launch { interactor.getSearchList().handleState(_loadingSearchListState) }
        }
    }

    override fun onRelease() {
        SearchHolder.release()
    }
}



