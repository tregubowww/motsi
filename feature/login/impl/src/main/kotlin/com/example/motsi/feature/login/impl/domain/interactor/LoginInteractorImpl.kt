package com.example.motsi.feature.login.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.feature.login.impl.domain.repository.LoginRepository
import javax.inject.Inject

internal class LoginInteractorImpl @Inject constructor(
    private val repository: LoginRepository
) : LoginInteractor {

    override suspend fun validateUsername(username: String): String? {
        if (username.isEmpty()) return "Имя пользователя обязательно"
        return when (val result = repository.validateUsername(username)) {
            is ResultWrapper.Success -> result.value.errorMessage
            is ResultWrapper.Error -> result.error.message
        }
    }


    override suspend fun validateEmail(email: String): String? {
        if (email.isEmpty()) return "Email обязателен"
        return when (val result = repository.validateEmail(email)) {
            is ResultWrapper.Success -> result.value.errorMessage
            is ResultWrapper.Error -> result.error.message
        }
    }

    override suspend fun validatePassword(password: String): String? {
        if (password.isEmpty()) return "Пароль обязателен"
        return when (val result = repository.validatePassword(password)) {
            is ResultWrapper.Success -> result.value.errorMessage
            is ResultWrapper.Error -> result.error.message
        }
    }

    override suspend fun validatePasswordMatch(
        password1: String,
        password2: String
    ): String? {
        return when {
            password2.isEmpty() -> "Повторите пароль"
            password1 != password2 -> "Пароли не совпадают"
            else -> null
        }
    }

    override suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ) {
        //Дописать userId и isSuccess. Нужно принимать и отправлять во ViewModel
        repository.registerUser(username, email, password)
    }
}