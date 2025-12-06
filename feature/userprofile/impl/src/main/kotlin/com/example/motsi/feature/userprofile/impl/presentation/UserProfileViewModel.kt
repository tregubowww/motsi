package com.example.motsi.feature.userprofile.impl.presentation

import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.EffectHandler
import com.example.motsi.core.common.presentation.UiReducer
import com.example.motsi.core.common.presentation.emitTo
import com.example.motsi.core.common.presentation.utils.handleState
import com.example.motsi.feature.userprofile.impl.di.UserProfileHolder
import com.example.motsi.feature.userprofile.impl.domain.interactor.UserProfileInteractor
import com.example.motsi.feature.userprofile.impl.models.presentation.UserProfileScreenIntent
import com.example.motsi.feature.userprofile.impl.models.presentation.UserProfileScreenEffect
import com.example.motsi.feature.userprofile.impl.models.presentation.UserProfileScreenState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class UserProfileViewModel @Inject constructor(
    private val interactor: UserProfileInteractor,
) : BaseViewModel<UserProfileScreenIntent>() {

    /** Состояние экрана */
    private val screenReducer =
        UiReducer(UserProfileScreenState(loadingState = LoadingState.Loading))
    val screenState: StateFlow<UserProfileScreenState> get() = screenReducer.state

    /** Эффекты */
    private val effectHandler = EffectHandler<UserProfileScreenEffect>()
    val effect: SharedFlow<UserProfileScreenEffect> get() = effectHandler.effect

    init {
        loadInitialData()
    }

    /** Универсальная функция отправки Intent */
    override fun dispatch(intent: UserProfileScreenIntent) {
        when (intent) {
            is UserProfileScreenIntent.ClickOnSubscriptionsPickList -> {
                UserProfileScreenEffect.NavigateToSubscriptionsScreen.emitTo(
                    viewModelScope, effectHandler
                )
            }

            is UserProfileScreenIntent.ClickOnSubscribersPickList -> {
                UserProfileScreenEffect.NavigateToSubscribersScreen.emitTo(
                    viewModelScope, effectHandler
                )
            }

            is UserProfileScreenIntent.ClickOnEditUserProfileScreen -> {
                UserProfileScreenEffect.NavigateToEditUserProfileScreen.emitTo(
                    viewModelScope, effectHandler
                )
            }

            is UserProfileScreenIntent.ClickOnSendUserProfile -> {
                UserProfileScreenEffect.SendUserProfile(intent.userUrl)
                    .emitTo(viewModelScope, effectHandler)
            }

            is UserProfileScreenIntent.ClickOnLevel -> {
                UserProfileScreenEffect.NavigateToSportTypeLevelInfoScreen.emitTo(
                    viewModelScope, effectHandler
                )
            }
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val screenResult = interactor.getUserProfileScreen().handleState()
            screenReducer.update { copy(loadingState = screenResult) }
        }
    }

    // Функция для форматирования чисел
    internal fun formatNumberFollowers(count: String): String {
        return try {
            val number = count.toLong()
            when {
                number >= 1_000_000 -> "${number / 1_000_000}M"
                number >= 1_000 -> "${number / 1_000}K"
                else -> count
            }
        } catch (e: Exception) {
            count
        }
    }

    override fun onRelease() = UserProfileHolder.release()
}