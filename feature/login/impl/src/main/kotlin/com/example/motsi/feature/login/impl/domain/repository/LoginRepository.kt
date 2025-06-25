package com.example.motsi.feature.login.impl.domain.repository

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.login.impl.models.domain.RegisterScreenDomainModel
import com.example.motsi.feature.login.impl.models.domain.ValidationResultDomain
import com.example.motsi.feature.login.impl.presentation.view_model.RegistrationResult

internal interface LoginRepository {

    suspend fun getRegisterScreen(): ResultWrapper<RegisterScreenDomainModel, MotsiError>

    suspend fun validateUsername(username: String): ResultWrapper<ValidationResultDomain, MotsiError>
    suspend fun validateEmail(email: String): ResultWrapper<ValidationResultDomain, MotsiError>
    suspend fun validatePassword(password: String): ResultWrapper<ValidationResultDomain, MotsiError>
    suspend fun validatePasswordMath(password: String): ResultWrapper<ValidationResultDomain, MotsiError>

    suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): ResultWrapper<RegistrationResult, MotsiError>
}