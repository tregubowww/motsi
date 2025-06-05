package com.example.motsi.core.common.models.presentation


///** Состояние загрузки экрана */
//sealed class LoadingState<out T> {
//
//    /** Состояние загружается */
//    object Loading : LoadingState<Nothing>()
//
//    /** Состояние успешно загружено */
//    data class Success<out T>(val data: T) : LoadingState<T>()
//
//    /** Состояние с ошибкой */
//    data class Error<out E>(val error: E) : LoadingState<E>()
//}

sealed class LoadingState<out T, out E> {
    object Loading : LoadingState<Nothing, Nothing>()
    data class Success<out T>(val data: T) : LoadingState<T, Nothing>()
    data class Error<out E>(val error: E) : LoadingState<Nothing, E>()
}