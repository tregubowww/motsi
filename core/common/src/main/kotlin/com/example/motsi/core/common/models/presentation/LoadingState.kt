package com.example.motsi.core.common.models.presentation

sealed class LoadingState<out T, out E> {
    data object Idle : LoadingState<Nothing, Nothing>()
    data object Loading : LoadingState<Nothing, Nothing>()
    data class Success<out T>(val data: T) : LoadingState<T, Nothing>()
    data class Error<out E>(val error: E) : LoadingState<Nothing, E>()
}