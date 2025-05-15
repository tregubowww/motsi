package com.example.motsi.core.common.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.common.models.presentation.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow

//удалить
@Composable
inline fun <reified VM : ViewModel> injectedViewModel(
    key: String? = null,
    viewModelStoreOwner: ViewModelStoreOwner? = null,
    crossinline viewModelInstanceCreator: @DisallowComposableCalls () -> VM
): VM {
    val factory = remember(key) {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                // Проверяем, соответствует ли запрашиваемый класс VM
                return viewModelInstanceCreator() as T
            }
        }
    }
    // Используем viewModel() с правильным ключом и фабрикой
    return viewModel(
        key = key,
        factory = factory,
        viewModelStoreOwner = viewModelStoreOwner ?: checkNotNull(LocalViewModelStoreOwner.current)
    )
}

inline fun <T, E> ResultWrapper<T, E>.handleState(
    onSuccess: (T) -> Unit,
    onFailure: (E) -> Unit
) {
    when (this) {
        is ResultWrapper.Success -> onSuccess(value)
        is ResultWrapper.Error -> onFailure(error)
    }
}

fun <T, E> ResultWrapper<T, E>.handleState(
    stateFlow: MutableStateFlow<LoadingState<T, E>>
) {
    when (this) {
        is ResultWrapper.Success -> stateFlow.value = LoadingState.Success(value)
        is ResultWrapper.Error -> stateFlow.value = LoadingState.Error(error)
    }
}