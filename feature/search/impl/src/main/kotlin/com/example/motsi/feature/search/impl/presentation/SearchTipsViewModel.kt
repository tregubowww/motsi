package com.example.motsi.feature.search.impl.presentation

import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.feature.search.impl.di.SearchHolder
import com.example.motsi.feature.search.impl.domain.interactor.SearchInteractor
import com.example.motsi.feature.search.impl.models.domain.SearchTipsListModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SearchTipsViewModel @Inject constructor(
    private val interactor: SearchInteractor,
) : BaseViewModel() {

//    /** Состояние экрана подсказок */
//    val searchTipsScreenState: StateFlow<SearchTipsScreenState> get() = _searchTipsScreenState.asStateFlow()
//    private val _searchTipsScreenState =
//        MutableStateFlow(SearchTipsScreenState(loadingState = LoadingState.Loading))

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun initSearchQuery(searchQuery: String) {
        _searchQuery.value = searchQuery
    }

    fun onSearchQueryChange(text: String) {
        _searchQuery.value = text
    }

    private val _listTipsState = MutableStateFlow(SearchTipsListModel())
    val listTipsState: StateFlow<SearchTipsListModel> = _listTipsState


    fun getSearchTips(searchQuery: String) {
        viewModelScope.launch {
            launch {
                interactor.getSearchTips(searchQuery)
                }
            }

    }

    init {
        viewModelScope.launch {
            when (val result = interactor.getSearchTips(searchQuery.value)) {
                is ResultWrapper.Success -> {
                    _listTipsState.value = result.value
                }

                is ResultWrapper.Error -> {
                    // Обработка ошибки (добавить состояние ошибки)
                }

            }
        }
        //  _tipsListState.value =
        // SearchScreenState(loadingState = interactor.getSearchScreen().handleState())

    }

    override fun onInit() {

    }

    override fun onRelease() {
        SearchHolder.release()
    }
}