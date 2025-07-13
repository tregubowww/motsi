package com.example.motsi.feature.login.impl.models.domain

data class UsernameValidationResult(
    val errorMessage: String?
)

data class EmailValidationResult(
    val errorMessage: String?
)

data class PasswordValidationResult(
    val errorMessage: String?
)

data class RegistrationResult(
    val isSuccess: Boolean,
    val userId: Long?
)
