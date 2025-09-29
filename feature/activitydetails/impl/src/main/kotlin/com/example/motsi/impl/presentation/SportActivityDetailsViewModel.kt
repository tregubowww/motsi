package com.example.motsi.impl.presentation

import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.utils.handleState
import com.example.motsi.impl.di.SportActivityDetailsHolder
import com.example.motsi.impl.domain.interactor.SportActivityDetailsInteractor
import com.example.motsi.impl.models.presentation.SportActivityDetailsScreenIntent
import com.example.motsi.impl.models.presentation.SportActivityDetailsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SportActivityDetailsViewModel @Inject constructor(
    private val interactor: SportActivityDetailsInteractor,
) : BaseViewModel() {

    /** Состояние загрузки экрана */
    val screenState: StateFlow<SportActivityDetailsScreenState> get() = _screenState.asStateFlow()
    private val _screenState =
        MutableStateFlow(SportActivityDetailsScreenState(loadingState = LoadingState.Loading))

    fun initViewModel(id: String) {
        viewModelScope.launch {
            launch {
                val state = interactor.getSportActivityDetailsScreen(id).handleState()
                _screenState.value =
                    SportActivityDetailsScreenState(
                        loadingState = state,
                        isFavorites = (state as? LoadingState.Success)?.data?.isFavorites ?: false
                    )
            }
        }
    }


    fun onScreenIntent(intent: SportActivityDetailsScreenIntent) {
        when (intent) {
            is SportActivityDetailsScreenIntent.ChangeFavoritesStatus -> {
                _screenState.value = _screenState.value.copy(isFavorites = screenState.value.isFavorites.not())
            }
            is SportActivityDetailsScreenIntent.SendSportActivity -> {
//                отпрарвка ссылки
            }

            is SportActivityDetailsScreenIntent.AddSportActivity -> {
//                добавить активность
            }
            is SportActivityDetailsScreenIntent.ShowInfoAboutPrivateStatus -> {
//          показать штору о приватном статусе
            }
            is SportActivityDetailsScreenIntent.ShowInfoLevelSportActivity -> {
//            показать штору об уровне
            }
            is SportActivityDetailsScreenIntent.OpenChatSportActivity -> {
//                открыть чат активности
            }
        }
    }

    override fun onRelease() {
        SportActivityDetailsHolder.release()
    }
}