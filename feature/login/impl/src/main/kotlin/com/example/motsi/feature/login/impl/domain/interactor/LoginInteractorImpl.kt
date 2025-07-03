package com.example.motsi.feature.login.impl.domain.interactor

import com.example.motsi.feature.login.impl.domain.repository.LoginRepository
import javax.inject.Inject

internal class LoginInteractorImpl @Inject constructor(
    private val repository: LoginRepository
) : LoginInteractor {

    override suspend fun validateUsername(username: String): String? {
        return repository.validateUsername(username)
    }

    override suspend fun validateEmail(email: String): String? {
        return repository.validateEmail(email)
    }

    override suspend fun validatePassword(password: String): String? {
        return repository.validatePassword(password)
    }

    override suspend fun validatePasswordMatch(password1: String, password2: String): String? {
        return repository.validatePasswordMatch(password1, password2)
    }

    override suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): Boolean = repository.registerUser(username, email, password)
}