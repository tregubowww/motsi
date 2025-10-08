package com.example.motsi.feature.addsportactivity.impl.presentation.choicetypesport

import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.utils.handleState
import com.example.motsi.feature.addsportactivity.impl.domain.interactor.AddSportActivityInteractor
import com.example.motsi.feature.addsportactivity.impl.models.presentation.ChoiceTypeSportScreenIntent
import com.example.motsi.feature.addsportactivity.impl.models.presentation.ChoiceTypeSportState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class ChoiceTypeSportViewModel @Inject constructor(
    private val interactor: AddSportActivityInteractor,
) : BaseViewModel() {

    val choiceTypeSportScreenState: StateFlow<ChoiceTypeSportState> get() = _choiceTypeSportScreenState.asStateFlow()
    private val _choiceTypeSportScreenState =
        MutableStateFlow(ChoiceTypeSportState(loadingState = LoadingState.Loading))

    val searchQuery: StateFlow<String> get() = _searchQuery.asStateFlow()
    private val _searchQuery = MutableStateFlow("")

    fun initViewModel() {
        viewModelScope.launch {
            launch {
                _choiceTypeSportScreenState.value =
                    ChoiceTypeSportState(
                        loadingState = interactor.getChoiceTypeSportScreen().handleState()
                    )

            }
            launch {
                _searchQuery
                    .debounce(300)
                    .distinctUntilChanged()
                    .collect { query ->
                        filterListTypeSport(query)
                    }
            }
        }
    }

    fun onChoiceTypeSportScreenIntent(intent: ChoiceTypeSportScreenIntent) {
        when (intent) {
            is ChoiceTypeSportScreenIntent.ClickTypeSportField -> {
// открыть следующую страницу создания активности
            }

            is ChoiceTypeSportScreenIntent.FilerListTypeSport -> {
                _searchQuery.value = intent.text
            }
        }
    }

    private fun filterListTypeSport(text: String) {
        val list =
            (choiceTypeSportScreenState.value.loadingState as? LoadingState.Success)?.data?.listTypeSport
                ?: emptyList()
        val filteredList = if (text.isEmpty()) {
            list
        } else {
            list.filter { item ->
                item.name.contains(text, ignoreCase = true)
            }
        }
        _choiceTypeSportScreenState.value =
            choiceTypeSportScreenState.value.copy(listTypeSport = filteredList.toImmutableList())
    }

    override fun onRelease() {
//        nothing
    }
}