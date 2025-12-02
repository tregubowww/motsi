package com.example.motsi.feature.mysportactivities.impl.presentation

import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.EffectHandler
import com.example.motsi.core.common.presentation.UiReducer
import com.example.motsi.core.common.presentation.utils.handleState
import com.example.motsi.feature.mysportactivities.impl.di.MySportActivitiesHolder
import com.example.motsi.feature.mysportactivities.impl.domain.interactor.MySportActivitiesInteractor
import com.example.motsi.feature.mysportactivities.impl.models.presentation.MySportActivitiesIntent
import com.example.motsi.feature.mysportactivities.impl.models.presentation.MySportActivitiesScreenEffect
import com.example.motsi.feature.mysportactivities.impl.models.presentation.MySportActivitiesScreenState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class MySportActivitiesViewModel @Inject constructor(
    val interactor: MySportActivitiesInteractor,
) : BaseViewModel<MySportActivitiesIntent>() {

    private val screenReducer =
        UiReducer(MySportActivitiesScreenState(loadingState = LoadingState.Loading))
    val screenState: StateFlow<MySportActivitiesScreenState> get() = screenReducer.state

    private val effectHandler = EffectHandler<MySportActivitiesScreenEffect>()
    val effect: SharedFlow<MySportActivitiesScreenEffect> get() = effectHandler.effect

    init {
        loadInitialData()
    }


    override fun dispatch(intent: MySportActivitiesIntent) {
        when (intent) {
            is MySportActivitiesIntent.AddSportActivity -> {
                val url =
                    (screenState.value.loadingState as? LoadingState.Success)?.data?.addSportActivity?.urlStartWizardAddSportActivity

                url?.let {
                    viewModelScope.launch {
                        effectHandler.emit(
                            MySportActivitiesScreenEffect.OpenAddSportActivityScreen(
                                it
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onRelease() = MySportActivitiesHolder.release()

    private fun loadInitialData() {
        viewModelScope.launch {
            val state = interactor.getDataScreen().handleState()
            screenReducer.update { copy(loadingState = state) }
        }
    }
}