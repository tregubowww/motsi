package com.example.motsi.feature.search.impl.presentation

import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.EffectHandler
import com.example.motsi.core.common.presentation.UiReducer
import com.example.motsi.core.common.presentation.utils.handleState
import com.example.motsi.feature.search.impl.domain.interactor.SearchInteractor
import com.example.motsi.feature.search.impl.models.domain.SearchTip
import com.example.motsi.feature.search.impl.models.domain.SearchTipsListModel
import com.example.motsi.feature.search.impl.models.presentation.SearchTipsDestination
import com.example.motsi.feature.search.impl.models.presentation.tips.SearchTipListEffect
import com.example.motsi.feature.search.impl.models.presentation.tips.SearchTipListIntent
import com.example.motsi.feature.search.impl.models.presentation.tips.SearchTipListState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

internal class SearchTipsViewModel @AssistedInject constructor(
    private val interactor: SearchInteractor,
    @Assisted val entryData: SearchTipsDestination.EntryData
) : BaseViewModel<SearchTipListIntent>() {

    val searchQuery: StateFlow<String> get() = _searchQuery.asStateFlow()
    private val _searchQuery = MutableStateFlow("")

    /** Состояние экрана */
    private val screenReducer =
        UiReducer(SearchTipListState(loadingState = LoadingState.Loading))
    val screenState: StateFlow<SearchTipListState> get() = screenReducer.state

    /** Эффекты */
    private val effectHandler = EffectHandler<SearchTipListEffect>()
    val effect: SharedFlow<SearchTipListEffect> get() = effectHandler.effect

    private var historyTipList: List<SearchTip> = emptyList()

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(DEBOUNCE)
                .mapLatest { query ->
                    if (query.isNotBlank()) {
                        interactor.getTipList(query).handleState()
                    } else {
                        LoadingState.Success(SearchTipsListModel(tipList = historyTipList.toImmutableList()))
                    }
                }
                .collect { loadingState ->
                    screenReducer.update { SearchTipListState(loadingState = loadingState) }
                }
        }
        screenReducer.update {
            SearchTipListState(
                loadingState = LoadingState.Success(data = SearchTipsListModel(tipList = entryData.historyTipList.toImmutableList()))
            )
        }
        entryData.searchQuery?.let { query ->
            viewModelScope.launch {
                delay(2000)
                val result = interactor.getTipList(query).handleState()
                screenReducer.update { SearchTipListState(loadingState = result) }
            }
        }
    }

    override fun dispatch(intent: SearchTipListIntent) {
        when (intent) {
            is SearchTipListIntent.OnSearchQueryChange -> {
                _searchQuery.value = intent.searchQuery
            }

            is SearchTipListIntent.TipClick -> {
                SearchTipListEffect.NavigateToSearchScreenWithNewData(
                    intent.navController, intent.type, intent.value
                ).emit()
            }

            is SearchTipListIntent.BackClick -> {
                SearchTipListEffect.NavigateToBackStack(intent.navController).emit()
            }
        }
    }

    private fun SearchTipListEffect.emit() =
        viewModelScope.launch { effectHandler.emit(this@emit) }

    override fun onRelease() = Unit

    @AssistedFactory
    interface Factory {
        fun create(entryData: SearchTipsDestination.EntryData): SearchTipsViewModel
    }

    private companion object {
        const val DEBOUNCE = 500L
    }
}