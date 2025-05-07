package com.example.motsi.feature.login.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class LoginViewModel @Inject constructor() : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Loading)
    val loginState: StateFlow<LoginState> = _loginState

    fun onLoginClicked(login: String, password: String) {
        _loginState.value = LoginState.Loading
        // Здесь добавить вызов к бэкенду для авторизации
        viewModelScope.launch {
            try {
                val response = loginUser(login, password) // Это вызов API
                _loginState.value = LoginState.Success(response)
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun onRegisterClicked(login: String, password1: String, password2: String) {
        _loginState.value = LoginState.Loading
        // Здесь добавить вызов к бэкенду для регистрации
        viewModelScope.launch {
            try {
                val response = registerUser(login, password1, password2) // Это вызов API
                _loginState.value = LoginState.Success(response)
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    private suspend fun loginUser(login: String, password: String): User {
        // Вызов своего API для авторизации
        return User(login, "token") // заглушка
    }

    private suspend fun registerUser(login: String, password1: String, password2: String): User {
        // Вызов своего API для регистрации
        return User(login, "token") // заглушка
    }
}

sealed class LoginState {
    data object Loading : LoginState()
    data class Success(val user: User) : LoginState()
    data class Error(val message: String) : LoginState()
}

data class User(val login: String, val token: String)