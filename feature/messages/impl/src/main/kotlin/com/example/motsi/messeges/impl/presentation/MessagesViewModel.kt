package com.example.motsi.messeges.impl.presentation

import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.EffectHandler
import com.example.motsi.messeges.impl.di.MessagesHolder
import com.example.motsi.messeges.impl.models.presentation.MessagesScreenEffect
import com.example.motsi.messeges.impl.models.presentation.MessagesScreenIntent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class MessagesViewModel @Inject constructor(
) : BaseViewModel<MessagesScreenIntent>() {

    /** Состояние экрана */
//    private val screenReducer =
//        UiReducer(MessagesScreenState(loadingState = LoadingState.Loading))
//    val screenState: StateFlow<MessagesScreenState> get() = screenReducer.state

    /** Эффекты */
    private val effectHandler = EffectHandler<MessagesScreenEffect>()
    val effect: SharedFlow<MessagesScreenEffect> get() = effectHandler.effect

    init {
    }

    /** Универсальная функция отправки Intent */
    override fun dispatch(intent: MessagesScreenIntent) {
    }

    private fun MessagesScreenEffect.emit() =
        viewModelScope.launch { effectHandler.emit(this@emit) }

    override fun onRelease() {
        MessagesHolder.release()
    }
}