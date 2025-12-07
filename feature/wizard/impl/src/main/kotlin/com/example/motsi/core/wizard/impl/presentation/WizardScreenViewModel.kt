package com.example.motsi.core.wizard.impl.presentation

import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.EffectHandler
import com.example.motsi.core.common.presentation.UiReducer
import com.example.motsi.core.common.presentation.utils.handleState
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.core.wizard.impl.di.WizardHolder
import com.example.motsi.core.wizard.impl.interactor.WizardInteractor
import com.example.motsi.core.wizard.impl.models.domain.WizardScreenModel
import com.example.motsi.core.wizard.impl.models.presentation.wizardcoordinator.WizardCoordinatorIntent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class WizardScreenViewModel @Inject constructor(
    private val interactor: WizardInteractor,
) : BaseViewModel<WizardCoordinatorIntent>() {

    private val reducer =
        UiReducer<LoadingState<WizardScreenModel, MotsiError>>(LoadingState.Loading)

    val state: StateFlow<LoadingState<WizardScreenModel, MotsiError>> = reducer.state

    private val effectHandler = EffectHandler<WizardScreenModel.Action>()
    val effect: SharedFlow<WizardScreenModel.Action> = effectHandler.effect

    fun loadStep(
        step: Int,
        properties: Map<String, String>
    ) {
        viewModelScope.launch {
            interactor.getNextScreen(
                step = step,
                properties = properties
            ).handleState(reducer)
        }
    }

    override fun dispatch(intent: WizardCoordinatorIntent) {
        when (intent) {
            is WizardCoordinatorIntent.OnAction -> {
                viewModelScope.launch {
                    effectHandler.emit(intent.action)
                }
            }
        }
    }

    override fun onRelease() {
        WizardHolder.release()
    }
}
