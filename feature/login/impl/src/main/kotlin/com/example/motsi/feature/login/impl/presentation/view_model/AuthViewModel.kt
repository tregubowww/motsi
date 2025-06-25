package com.example.motsi.feature.login.impl.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.feature.login.impl.di.LoginHolder
import com.example.motsi.feature.login.impl.domain.interactor.LoginInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class AuthViewModel @Inject constructor(
    private val interactor: LoginInteractor
) : BaseViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state


    fun validateAndLogin(
        login: String,
        password: String
    ): Boolean {
        val response = loginUser(login, password) // Вызов API
        if (login.isNotEmpty() && password.isNotEmpty()) {
            viewModelScope.launch {
//                try {
//                    _loginState.value = LoginState.Loading
//                    _loginState.value = LoginState.Success(response)
//                } catch (e: Exception) {
//                    _loginState.value = LoginState.Error(e.localizedMessage ?: "Ошибка авторизации")
//                }
            }
        }
        return response
    }



    private fun loginUser(login: String, password: String): Boolean{
        // Вызов своего API для авторизации
        return true // заглушка
    }

    private suspend fun registerUser(login: String, password1: String, password2: String): Boolean {
        // Вызов своего API для регистрации
        return true // заглушка
    }

    override fun onInit() {
        viewModelScope.launch {
//            launch { interactor.getSearchScreen().handleState(_loadingScreenState) }
//            launch { interactor.getSearchList().handleState(_loadingSearchListState) }
        }
    }

    override fun onRelease() {
        LoginHolder.release()
    }
}