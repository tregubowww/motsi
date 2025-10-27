package com.example.motsi.core.common.presentation.utils

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.common.models.presentation.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow

fun <T, E> ResultWrapper<T, E>.handleState(
    stateFlow: MutableStateFlow<LoadingState<T, E>>
) {
    when (this) {
        is ResultWrapper.Success -> stateFlow.value = LoadingState.Success(value)
        is ResultWrapper.Error -> stateFlow.value = LoadingState.Error(error)
    }
}

fun <T, E> ResultWrapper<T, E>.handleState(eventOnSuccess:(T)-> Unit = {}, eventOnError:(E)-> Unit = {}) =
    when (this) {
        is ResultWrapper.Success -> {
            eventOnSuccess.invoke(value)
            LoadingState.Success(value)
        }
        is ResultWrapper.Error -> {
            eventOnError.invoke(error)
            LoadingState.Error(error)
        }
    }