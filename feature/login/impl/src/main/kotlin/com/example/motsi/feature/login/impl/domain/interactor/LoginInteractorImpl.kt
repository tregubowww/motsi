package com.example.motsi.feature.login.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.login.impl.domain.repository.LoginRepository
import com.example.motsi.feature.login.impl.models.domain.RegisterScreenDomainModel
import com.example.motsi.feature.login.impl.models.domain.ValidationResultDomain
import com.example.motsi.feature.login.impl.presentation.view_model.RegistrationResult
import javax.inject.Inject

internal class LoginInteractorImpl @Inject constructor(
    private val repository: LoginRepository
) : LoginInteractor {

    override suspend fun getRegisterScreen(): ResultWrapper<RegisterScreenDomainModel, MotsiError> {
        return repository.getRegisterScreen()
    }

    override suspend fun validateUsername(username: String): ResultWrapper<ValidationResultDomain, MotsiError> {
        return repository.validateUsername(username)
    }

    override suspend fun validateEmail(email: String): ResultWrapper<ValidationResultDomain, MotsiError> {
        return repository.validateEmail(email)
    }

    override suspend fun validatePassword(password: String): ResultWrapper<ValidationResultDomain, MotsiError> {
        return repository.validatePassword(password)
    }

    override suspend fun validatePasswordMatch(password: String): ResultWrapper<ValidationResultDomain, MotsiError> {
        return repository.validatePasswordMath(password)
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): ResultWrapper<RegistrationResult, MotsiError> {
        return repository.registerUser(username, email, password)
    }
}

/** Старый код. Оставил на всякий. Удалить */

//internal class LoginInteractorImpl @Inject constructor(
//    private val repository: LoginRepository,
//    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
//) : LoginInteractor {
//
//    private val _validationState = MutableStateFlow(LoginInteractor.ValidationState())
//    override fun observeValidationState(): Flow<LoginInteractor.ValidationState> = _validationState
//
//    override suspend fun validateUsername(username: String): LoginInteractor.ValidationResult {
//        return when (val result = repository.validateUsername(username)) {
//            null -> {
//                _validationState.update { it.copy(usernameError = null) }
//                LoginInteractor.ValidationResult.Success(true)
//            }
//            else -> {
//                _validationState.update { it.copy(usernameError = result) }
//                LoginInteractor.ValidationResult.Success(false)
//            }
//        }
//    }
//
//    override suspend fun validateEmail(email: String): LoginInteractor.ValidationResult {
//        return when (val result = repository.validateEmail(email)) {
//            null -> {
//                _validationState.update { it.copy(emailError = null) }
//                LoginInteractor.ValidationResult.Success(true)
//            }
//            else -> {
//                _validationState.update { it.copy(emailError = result) }
//                LoginInteractor.ValidationResult.Success(false)
//            }
//        }
//    }
//
//    override suspend fun validatePassword(password: String): LoginInteractor.ValidationResult {
//        return when (val result = repository.validatePassword(password)) {
//            null -> {
//                _validationState.update { it.copy(passwordError = null) }
//                LoginInteractor.ValidationResult.Success(true)
//            }
//            else -> {
//                _validationState.update { it.copy(passwordError = result) }
//                LoginInteractor.ValidationResult.Success(false)
//            }
//        }
//    }
//
//    override suspend fun register(
//        username: String,
//        email: String,
//        password: String
//    ): LoginInteractor.RegistrationResult {
//        return try {
//            if (repository.registerUser(username, email, password)) {
//                LoginInteractor.RegistrationResult.Success
//            } else {
//                LoginInteractor.RegistrationResult.Error("Ошибка регистрации")
//            }
//        } catch (e: Exception) {
//            LoginInteractor.RegistrationResult.Error(e.message ?: "Неизвестная ошибка")
//        }
//    }
//}