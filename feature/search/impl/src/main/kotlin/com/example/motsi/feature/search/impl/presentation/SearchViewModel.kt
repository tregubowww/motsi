package com.example.motsi.feature.search.impl.presentation

import androidx.lifecycle.viewModelScope
import com.example.motsi.api.SportActivityDetailsGraph
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.utils.handleState
import com.example.motsi.feature.search.impl.di.SearchHolder
import com.example.motsi.feature.search.impl.domain.interactor.SearchInteractor
import com.example.motsi.feature.search.impl.models.presentation.SearchTipsDestination
import com.example.motsi.feature.search.impl.models.presentation.listactivity.SearchListActivityIntent
import com.example.motsi.feature.search.impl.models.presentation.listactivity.SearchListActivityState
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenIntent
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SearchViewModel @Inject constructor(
    private val interactor: SearchInteractor,
) : BaseViewModel() {

    /** Состояние загрузки экрана */
    val screenState: StateFlow<SearchScreenState> get() = _screenState.asStateFlow()
    private val _screenState =
        MutableStateFlow(SearchScreenState(loadingState = LoadingState.Loading))

    /** Состояние загрузки экрана */
    val listActivityState: StateFlow<SearchListActivityState> get() = _listActivityState.asStateFlow()
    private val _listActivityState =
        MutableStateFlow(SearchListActivityState(loadingState = LoadingState.Loading))

    fun initViewModel() {
        viewModelScope.launch {
            launch {
                _screenState.value =
                    SearchScreenState(loadingState = interactor.getSearchScreen().handleState())
            }
            launch {
                _listActivityState.value =
                    SearchListActivityState(
                        loadingState = interactor.getActivityList().handleState()
                    )
            }
        }
    }

    fun onScreenIntent(intent: SearchScreenIntent) {
        when (intent) {
            is SearchScreenIntent.ClickSearchField -> {
                intent.navController.navigate(
                    SearchTipsDestination(
                        entryData = SearchTipsDestination.EntryData(
                            searchQuery = intent.searchQuery,
                            searchHint = intent.searchHint,
                            historyTipList = intent.historyTipList
                        )
                    )
                )
            }
        }
    }

    fun onListActivityIntent(intent: SearchListActivityIntent) {
        when (intent) {
            is SearchListActivityIntent.ClickActivity -> {
                intent.navController.navigate(
                    SportActivityDetailsGraph
//            хардкод
                        (id = (0..100).random().toString()),
                )
            }
            is SearchListActivityIntent.AddFilter -> {
                viewModelScope.launch {
                    _listActivityState.value =
                        SearchListActivityState(
                            loadingState = interactor.getActivityList(filterData = intent.filterData)
                                .handleState()
                        )
                }
            }
        }
    }

    override fun onRelease() {
        SearchHolder.release()
    }
}