package com.example.motsi.core.navigation.presentation

import androidx.lifecycle.ViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedSplashScreenViewModel @Inject constructor() : ViewModel() {

    /** Состояние загрузки экрана */
    val isShowSplashScreen: StateFlow<Boolean> get() = _isShowSplashScreen.asStateFlow()
    private val _isShowSplashScreen = MutableStateFlow(true)

    fun hideSplashScreen() {
        _isShowSplashScreen.value = false
    }
}
