package com.example.motsi.feature.search.impl.presentation

import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.feature.search.impl.di.SearchHolder
import com.example.motsi.feature.search.impl.domain.interactor.SearchInteractor
import com.example.motsi.feature.search.impl.models.domain.SearchTipsListModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SearchTipsViewModel @Inject constructor(
    private val interactor: SearchInteractor,
) : BaseViewModel() {

//    /** Состояние экрана подсказок */
//    val searchTipsScreenState: StateFlow<SearchTipsScreenState> get() = _searchTipsScreenState.asStateFlow()
//    private val _searchTipsScreenState =
//        MutableStateFlow(SearchTipsScreenState(loadingState = LoadingState.Loading))

    val searchQuery: StateFlow<String> = interactor.searchQuery

    fun onSearchQueryChange(textSearchField: String) {
        viewModelScope.launch {
            interactor.updateSearchQuery(textSearchField)
        }
    }

    private val _listTipsState = MutableStateFlow(SearchTipsListModel())
    val listTipsState: StateFlow<SearchTipsListModel> = _listTipsState


    fun getSearchQuery(searchQuery: String) {
        viewModelScope.launch {
            launch {
//              //Написать запрос в API для получения списка активностей
                //interactor.getSearchQueryList(searchQuery)
                }
            }

    }

    init {
        viewModelScope.launch {
            launch {
                when (val result = interactor.getSearchTipsList()) {
                    is ResultWrapper.Success -> {
                        _listTipsState.value = result.value
                    }
                    is ResultWrapper.Error -> {
                        // Обработка ошибки (можно добавить состояние ошибки)
                    }

                }
            }
            //  _tipsListState.value =
            // SearchScreenState(loadingState = interactor.getSearchScreen().handleState())
        }
    }

    override fun onInit() {

    }

    override fun onRelease() {
        SearchHolder.release()
    }
}