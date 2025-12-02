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
    val interactor: WizardInteractor,
) : BaseViewModel<WizardCoordinatorIntent>() {

    private val currentScreenReducer =
        UiReducer<LoadingState<WizardScreenModel, MotsiError>>(LoadingState.Loading)
    val currentScreenState: StateFlow<LoadingState<WizardScreenModel, MotsiError>> get() = currentScreenReducer.state

    private val effectHandler = EffectHandler<WizardScreenModel.Action>()
    val effect: SharedFlow<WizardScreenModel.Action> get() = effectHandler.effect

    private var listScreen: List<WizardScreenModel> = emptyList()
    private var currentStep: Int = 1

    init {
        viewModelScope.launch {
            interactor.getNextScreen(
                properties = emptyMap(),
                step = currentStep
            ).handleState(eventOnSuccess = { screen ->
                currentScreenReducer.update { LoadingState.Success(screen) }
                listScreen = listOf(screen)
            })
        }
    }


    override fun dispatch(intent: WizardCoordinatorIntent) {
        when (intent) {
            is WizardCoordinatorIntent.OnAction -> {
                viewModelScope.launch {
                    handleAction(intent.action)
                }
            }
        }
    }

    private fun handleAction(action: WizardScreenModel.Action) {
        when (action) {
            is WizardScreenModel.Action.PreviewScreen -> {
                loadPreviewScreen()
            }
            is WizardScreenModel.Action.NextScreen -> {
                loadNextScreen(action.properties)
            }

            is WizardScreenModel.Action.Deeplink,
            is WizardScreenModel.Action.ShowSnackBar,
            is WizardScreenModel.Action.ExitWizardFlow -> {
                viewModelScope.launch {
                    effectHandler.emit(action)
                }
            }
        }
    }

    private fun loadPreviewScreen() {
        if (listScreen.size > 1) {
            currentStep -= 1
            listScreen = listScreen.dropLast(1)
            currentScreenReducer.update { LoadingState.Success(listScreen.last()) }
        } else {
            viewModelScope.launch {
                effectHandler.emit(WizardScreenModel.Action.ExitWizardFlow(emptyMap()))
            }
        }
    }

    private fun loadNextScreen(properties: Map<String, String>) {
        viewModelScope.launch {
            interactor.getNextScreen(
                properties = properties,
                step = currentStep + 1
            ).handleState(eventOnSuccess = { screen ->
                currentStep += 1
                currentScreenReducer.update { LoadingState.Success(screen) }
                listScreen = listScreen.plus(screen)
            })
        }
    }

    override fun onRelease() = WizardHolder.release()
}