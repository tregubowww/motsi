package com.example.motsi.core.common.models.data



sealed class ResultWrapper<out T, out E> {
    data class Success<out T>(val value: T) : ResultWrapper<T, Nothing>()
    data class Error<out E>(val error: E) : ResultWrapper<Nothing, E>()
}