package com.example.motsi.core.wizard.impl.presentation.widgets.searchwizardwidget

import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.EffectHandler
import com.example.motsi.core.common.presentation.UiReducer
import com.example.motsi.core.common.presentation.utils.handleState
import com.example.motsi.core.common.presentation.validator.ValidateState
import com.example.motsi.core.common.presentation.validator.validate
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.core.wizard.impl.interactor.WizardInteractor
import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel
import com.example.motsi.core.wizard.impl.models.presentation.searchwizard.SearchWizardEffect
import com.example.motsi.core.wizard.impl.models.presentation.searchwizard.SearchWizardIntent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

internal class SearchWizardWidgetViewModel @AssistedInject constructor(
    val interactor: WizardInteractor,
    @Assisted val widgetModel: WizardCoordinatorModel.Widget
) : BaseViewModel<SearchWizardIntent>() {

    private val itemListReducer =
        UiReducer<LoadingState<List<WizardCoordinatorModel.Widget.Item>, MotsiError>>(LoadingState.Loading)
    val itemListState: StateFlow<LoadingState<List<WizardCoordinatorModel.Widget.Item>, MotsiError>> get() = itemListReducer.state

    private val searchQueryReducer = UiReducer<ValidateState>(ValidateState.Valid(""))
    private val searchQuery: StateFlow<ValidateState> get() = searchQueryReducer.state

    private val effectHandler = EffectHandler<SearchWizardEffect>()
    val effect: SharedFlow<SearchWizardEffect> get() = effectHandler.effect


    init {
        itemListReducer.update { LoadingState.Success(widgetModel.items.orEmpty()) }
        viewModelScope.launch {
            searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    viewModelScope.launch {
                        widgetModel.properties?.get(PROPERTIES_URL_ITEMS_KEY)?.let {
                            interactor.getItems(query.value, it)
                                .handleState(eventOnSuccess = { list ->
                                    itemListReducer.update {
                                        LoadingState.Success(list)
                                    }
                                })
                        }
                    }
                }
        }
    }


    override fun dispatch(intent: SearchWizardIntent) {
        when (intent) {
            is SearchWizardIntent.ChangeSearchQuery -> {
                widgetModel.validators?.validate(intent.query)?.let {
                    searchQueryReducer.update { it }
                }
            }

            is SearchWizardIntent.ClickTip -> {
                widgetModel.validators?.validate(intent.item.title)?.let {
                    searchQueryReducer.update { it }
                }
                viewModelScope.launch {
                    effectHandler.emit(SearchWizardEffect.OnClickItem(intent.item))
                }
            }
        }
    }

    override fun onRelease() = Unit

    @AssistedFactory
    interface Factory {
        fun create(widgetModel: WizardCoordinatorModel.Widget): SearchWizardWidgetViewModel
    }

    private companion object {
        const val PROPERTIES_URL_ITEMS_KEY = "PROPERTIES_URL_ITEMS_KEY"
    }
}
