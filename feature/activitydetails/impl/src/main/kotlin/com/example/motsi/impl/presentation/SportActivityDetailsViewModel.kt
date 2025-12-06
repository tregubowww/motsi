package com.example.motsi.impl.presentation

import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.EffectHandler
import com.example.motsi.core.common.presentation.UiReducer
import com.example.motsi.core.common.presentation.utils.handleState
import com.example.motsi.impl.di.SportActivityDetailsHolder
import com.example.motsi.impl.domain.interactor.SportActivityDetailsInteractor
import com.example.motsi.impl.models.presentation.SportActivityDetailsScreenEffect
import com.example.motsi.impl.models.presentation.SportActivityDetailsScreenIntent
import com.example.motsi.impl.models.presentation.SportActivityDetailsScreenState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SportActivityDetailsViewModel @Inject constructor(
    private val interactor: SportActivityDetailsInteractor,
) : BaseViewModel<SportActivityDetailsScreenIntent>() {

    /** Состояние экрана */
    private val screenReducer =
        UiReducer(SportActivityDetailsScreenState(loadingState = LoadingState.Loading))
    val screenState: StateFlow<SportActivityDetailsScreenState> get() = screenReducer.state

    /** Эффекты */
    private val effectHandler = EffectHandler<SportActivityDetailsScreenEffect>()
    val effect: SharedFlow<SportActivityDetailsScreenEffect> get() = effectHandler.effect

    fun initViewModel(id: String) {
        viewModelScope.launch {
            launch {
                val state = interactor.getSportActivityDetailsScreen(id).handleState()
                screenReducer.update {
                    SportActivityDetailsScreenState(
                        loadingState = state,
                        isFavorites = (state as? LoadingState.Success)?.data?.isFavorites ?: false
                    )
                }
            }
        }
    }

    override fun dispatch(intent: SportActivityDetailsScreenIntent) {
        when (intent) {
            is SportActivityDetailsScreenIntent.ClickOnFavoritesStatus -> {
                screenReducer.update {
                    copy(isFavorites = screenState.value.isFavorites.not())
                }
            }

            is SportActivityDetailsScreenIntent.ClickOnIconSendSportActivity -> {
                SportActivityDetailsScreenEffect.SendSportActivity.emit()
            }

            is SportActivityDetailsScreenIntent.ClickOnAddSportActivity -> {
                SportActivityDetailsScreenEffect.AddSportActivity.emit()
            }

            is SportActivityDetailsScreenIntent.ClickOnIconPrivateStatus -> {
                SportActivityDetailsScreenEffect.ShowInfoAboutPrivateStatus.emit()
            }

            is SportActivityDetailsScreenIntent.ClickOnIconLevelSportActivity -> {
                SportActivityDetailsScreenEffect.ShowInfoLevelSportActivity.emit()
            }

            is SportActivityDetailsScreenIntent.ClickOnOpenChatSportActivity -> {
                SportActivityDetailsScreenEffect.OpenChatSportActivity.emit()
            }
        }
    }

    private fun SportActivityDetailsScreenEffect.emit() =
        viewModelScope.launch { effectHandler.emit(this@emit) }

    override fun onRelease() {
        SportActivityDetailsHolder.release()
    }
}