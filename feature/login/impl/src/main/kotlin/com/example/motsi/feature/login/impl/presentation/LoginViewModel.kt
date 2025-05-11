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

    // Состояния ошибок для каждого поля
    private val _usernameError = MutableStateFlow<String?>(null)
    val usernameError: StateFlow<String?> = _usernameError

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError

    private val _password1Error = MutableStateFlow<String?>(null)
    val password1Error: StateFlow<String?> = _password1Error

    private val _password2Error = MutableStateFlow<String?>(null)
    val password2Error: StateFlow<String?> = _password2Error

    // Проверка всех полей перед регистрацией
    private fun validateRegistration(
        username: String,
        email: String,
        password1: String,
        password2: String,
        errorMessages: Map<String, String>
    ): Boolean {
        var isValid = true

        // Проверка имени пользователя
        if (username.isEmpty()) {
            _usernameError.value = errorMessages["username_required"]
            isValid = false
        } else if (!isValidUsername(username)) {
            _usernameError.value = errorMessages["invalid_username_format"]
            isValid = false
        } else {
            _usernameError.value = null
        }

        // Проверка email
        if (email.isEmpty()) {
            _emailError.value = errorMessages["email_required"]
            isValid = false
        } else if (!isValidEmail(email)) {
            _emailError.value = errorMessages["invalid_email_format"]
            isValid = false
        } else {
            _emailError.value = null
        }

        // Проверка пароля 1
        if (password1.isEmpty()) {
            _password1Error.value = errorMessages["password_required"]
            isValid = false
        } else if (!isStrongPassword(password1)) {
            _password1Error.value = errorMessages["weak_password"]
            isValid = false
        } else {
            _password1Error.value = null
        }

        // Проверка пароля 2
        if (password2.isEmpty()) {
            _password2Error.value = errorMessages["password_repeat_required"]
            isValid = false
        } else if (password1 != password2) {
            _password2Error.value = errorMessages["passwords_mismatch"]
            isValid = false
        } else {
            _password2Error.value = null
        }

        return isValid
    }

    // Методы проверки. Вынести в отдельный утилитный класс?
    private fun isValidUsername(username: String): Boolean {
        val regex = Regex("^[\\p{L}0-9_.-]{2,30}$")
        return regex.matches(username)
    }

    private fun isValidEmail(email: String): Boolean {
        val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return regex.matches(email)
    }

    private fun isStrongPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#\$%^*()_+]).{8,}$")
        return regex.matches(password)
    }

    fun validateAndRegister(
        username: String,
        email: String,
        password1: String,
        password2: String,
        errorMessages: Map<String, String>
    ): Boolean {
        val isValid = validateRegistration(username, email, password1, password2, errorMessages)
        if (isValid) {
            viewModelScope.launch {
                try {
                    _loginState.value = LoginState.Loading
                    val response = registerUser(username, password1,password2) // Вызов API
                    _loginState.value = LoginState.Success(response)
                } catch (e: Exception) {
                    _loginState.value = LoginState.Error(e.localizedMessage ?: "Ошибка регистрации")
                }
            }
        }
        return isValid
    }


    fun validateAndLogin(
        login: String,
        password: String
    ): Boolean {
        val response = loginUser(login, password) // Вызов API
        if (login.isNotEmpty() && password.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    _loginState.value = LoginState.Loading
                    _loginState.value = LoginState.Success(response)
                } catch (e: Exception) {
                    _loginState.value = LoginState.Error(e.localizedMessage ?: "Ошибка авторизации")
                }
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
}

sealed class LoginState {
    data object Loading : LoginState()
    data class Success(val user: Boolean) : LoginState()
    data class Error(val message: String) : LoginState()
}
