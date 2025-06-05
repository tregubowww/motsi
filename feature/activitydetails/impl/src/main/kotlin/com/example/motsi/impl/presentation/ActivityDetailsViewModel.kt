package com.example.motsi.impl.presentation

import androidx.lifecycle.ViewModel
import com.example.motsi.core.common.models.presentation.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

internal class ActivityDetailsViewModel @Inject constructor(
) : ViewModel() {

    /** Состояние загрузки экрана */
    val loadingScreenState: StateFlow<String> get() = _loadingScreenState.asStateFlow()
    private val _loadingScreenState =
        MutableStateFlow<String>("")

    val counter: StateFlow<Int> get() = _counter.asStateFlow()
    private val _counter =
        MutableStateFlow<Int>(0)

    fun initVM(id: String) {
        _loadingScreenState.value = id
    }

    fun onClick() {
        _counter.value += 1
    }

}