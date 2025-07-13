package com.example.motsi.feature.login.impl.domain.repository

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.login.impl.models.domain.EmailValidationResult
import com.example.motsi.feature.login.impl.models.domain.PasswordValidationResult
import com.example.motsi.feature.login.impl.models.domain.RegistrationResult
import com.example.motsi.feature.login.impl.models.domain.UsernameValidationResult

internal interface LoginRepository {

    suspend fun validateUsername(username: String): ResultWrapper<UsernameValidationResult, MotsiError>

    suspend fun validateEmail(email: String): ResultWrapper<EmailValidationResult, MotsiError>

    suspend fun validatePassword(password: String): ResultWrapper<PasswordValidationResult, MotsiError>

    suspend fun registerUser(username: String, email: String, password: String): ResultWrapper<RegistrationResult, MotsiError>
}