package com.example.motsi.feature.login.impl.domain.repository

internal interface LoginRepository {

    suspend fun validateUsername(username: String): String?

    suspend fun validateEmail(email: String): String?

    suspend fun validatePassword(password: String): String?

    suspend fun validatePasswordMatch(password1: String, password2: String): String?

    suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): Boolean
}