package com.example.motsi.feature.search.impl.presentation

import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.utils.handleState
import com.example.motsi.feature.search.impl.domain.interactor.SearchInteractor
import com.example.motsi.feature.search.impl.models.domain.SearchTipsListModel
import com.example.motsi.feature.search.impl.models.presentation.SearchDestination
import com.example.motsi.feature.search.impl.models.presentation.SearchTipsDestination
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenIntent
import com.example.motsi.feature.search.impl.models.presentation.tips.SearchTipListIntent
import com.example.motsi.feature.search.impl.models.presentation.tips.SearchTipListState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SearchTipsViewModel @Inject constructor(
    private val interactor: SearchInteractor,
) : BaseViewModel() {

    val searchQuery: StateFlow<String> get() = _searchQuery.asStateFlow()
    private val _searchQuery = MutableStateFlow("")

    val tipListState: StateFlow<SearchTipListState> get() = _tipListState.asStateFlow()
    private val _tipListState =
        MutableStateFlow(SearchTipListState(loadingState = LoadingState.Loading))

    fun onSearchQueryChange(text: String) {
        _searchQuery.value = text
    }

    fun initViewModel(entryData: SearchTipsDestination.EntryData){
        _tipListState.value = SearchTipListState(
            loadingState = LoadingState.Success(data = SearchTipsListModel(tipList = entryData.historyTipList.toImmutableList()))
        )
        getSearchTips(entryData.searchQuery.orEmpty())
    }




    fun onTipListIntent(intent: SearchTipListIntent) {
        when (intent) {
            is SearchTipListIntent.LoadTipList -> {
                getSearchTips(intent.searchQuery)
            }
            is SearchTipListIntent.TipClick -> {
                intent.navController.popBackStack()
                intent.navController.navigate(
                    SearchDestination(
                        filterData = SearchDestination.SearchFilterData(
                            type = intent.type,
                            value = intent.value
                        )
                    )
                ) {
                    launchSingleTop = true
                }
            }
            is SearchTipListIntent.BackClick -> {
                intent.navController.popBackStack()
            }
        }
    }

    private fun getSearchTips(searchQuery: String) {
        viewModelScope.launch {
            delay(2000)
            _tipListState.value =
                SearchTipListState(
                    loadingState = interactor.getTipList(searchQuery).handleState()
                )
        }
    }

    override fun onRelease(){
        //nothing
    }
}