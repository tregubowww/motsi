package com.example.motsi.core.common.presentation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

// Эффекты
class EffectHandler<E : Any> {
    private val _effect = MutableSharedFlow<E>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effect: SharedFlow<E> get() = _effect

    suspend fun emit(effect: E) = _effect.emit(effect)
    fun tryEmit(effect: E) = _effect.tryEmit(effect)
}