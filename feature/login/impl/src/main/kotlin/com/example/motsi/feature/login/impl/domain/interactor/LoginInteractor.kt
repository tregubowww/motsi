package com.example.motsi.feature.login.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.login.impl.models.domain.RegisterScreenDomainModel
import com.example.motsi.feature.login.impl.models.domain.ValidationResultDomain
import com.example.motsi.feature.login.impl.presentation.view_model.RegistrationResult

internal interface LoginInteractor {

    /** Получить данные для экрана регистрации */
    suspend fun getRegisterScreen(): ResultWrapper<RegisterScreenDomainModel, MotsiError>

    /** Валидация полей */
    suspend fun validateUsername(username: String): ResultWrapper<ValidationResultDomain, MotsiError>
    suspend fun validateEmail(email: String): ResultWrapper<ValidationResultDomain, MotsiError>
    suspend fun validatePassword(password: String): ResultWrapper<ValidationResultDomain, MotsiError>
    suspend fun validatePasswordMatch(password: String): ResultWrapper<ValidationResultDomain, MotsiError>

    /** Регистрация пользователя */
    suspend fun register(
        username: String,
        email: String,
        password: String
    ): ResultWrapper<RegistrationResult, MotsiError>
}


/** Старый код. Оставил на всякий. Удалить */

//interface LoginInteractor {
//
//    // Валидация с debounce
//    suspend fun validateUsername(username: String): ValidationResult
//    suspend fun validateEmail(email: String): ValidationResult
//    suspend fun validatePassword(password: String): ValidationResult
//
//    // Регистрация
//    suspend fun register(
//        username: String,
//        email: String,
//        password: String
//    ): RegistrationResult
//
//    // Состояние
//    fun observeValidationState(): Flow<ValidationState>
//
//    sealed interface ValidationResult {
//        data class Success(val isValid: Boolean) : ValidationResult
//        data class Error(val message: String) : ValidationResult
//    }
//
//    sealed interface RegistrationResult {
//        object Success : RegistrationResult
//        data class Error(val message: String) : RegistrationResult
//    }
//
//    data class ValidationState(
//        val usernameError: String? = null,
//        val emailError: String? = null,
//        val passwordError: String? = null
//    )
//}