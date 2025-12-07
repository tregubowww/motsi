package com.example.motsi.core.common.presentation.utils

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.UiReducer

/**
 * Создаёт ViewModel через AssistedInject-фабрику в Compose.
 *
 * @param vmClass класс ViewModel
 * @param assistedFactory лямбда, создающая ViewModel с аргументом
 * @param args аргумент для ViewModel
 */
@Composable
fun <VM : ViewModel, Arg> assistedViewModel(
    vmClass: Class<VM>,
    assistedFactory: (Arg) -> VM,
    args: Arg,
    key: String? = null,
): VM = viewModel(
    key = key,
    modelClass = vmClass,
    factory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = assistedFactory(args) as T
    }
)

fun <T, E> ResultWrapper<T, E>.handleState(
    stateFlow: UiReducer<LoadingState<T, E>>
) {
    when (this) {
        is ResultWrapper.Success -> stateFlow.update { LoadingState.Success(value) }
        is ResultWrapper.Error -> stateFlow.update { LoadingState.Error(error) }
    }
}

fun <T, E> ResultWrapper<T, E>.handleState(
    eventOnSuccess: (T) -> Unit = {},
    eventOnError: (E) -> Unit = {}
) =
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