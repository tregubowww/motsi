package com.example.motsi.feature.login.impl.data.repository

import com.example.motsi.feature.login.impl.domain.repository.LoginRepository
import com.example.motsi.feature.login.impl.validator.StringValidator
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val validator: StringValidator
) : LoginRepository {

    override suspend fun validateUsername(username: String): String? {
        return when {
            username.isEmpty() -> "Имя пользователя обязательно"
            else -> validator.validateUsername(username)
        }
    }

    override suspend fun validateEmail(email: String): String? {
        return when {
            email.isEmpty() -> "Email обязателен"
            else -> validator.validateEmail(email)
        }
    }

    override suspend fun validatePassword(password: String): String? {
        return when {
            password.isEmpty() -> "Пароль обязателен"
            else -> validator.validatePassword(password)
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
    ): Boolean {
        // Реальный вызов API
        return true // Заглушка
    }
}



/** Новый кривой код. Оставил на всякий. Удалить */
//internal class LoginRepositoryImpl @Inject constructor(
//    private val remoteDataSource: LoginRemoteDataSource,
//    private val registerScreenConverter: RegisterScreenConverter,
//    private val validationConverter: ValidationConverter,
//    private val registrationConverter: RegistrationConverter,
//    private val apiResponseHandler: ApiResponseHandler
//) : LoginRepository {
//
//    override suspend fun getRegisterScreen(): ResultWrapper<RegisterScreenDomainModel, MotsiError> {
//        return apiResponseHandler.requestMapped(
//            call = { remoteDataSource.getRegisterScreen() },
//            mapper = registerScreenConverter::toDomain
//        )
//    }
//
//    override suspend fun validateUsername(username: String): ResultWrapper<ValidationResultDomain, MotsiError> {
//        return apiResponseHandler.requestMapped(
//            call = { remoteDataSource.validateUsername(ValidationRequest(username)) },
//            mapper = validationConverter::toDomain
//        )
//    }
//
//    override suspend fun validateEmail(email: String): ResultWrapper<ValidationResultDomain, MotsiError> {
//        return apiResponseHandler.requestMapped(
//            call = { remoteDataSource.validateEmail(ValidationRequest(email)) },
//            mapper = validationConverter::toDomain
//        )
//    }
//
//    override suspend fun validatePassword(password: String): ResultWrapper<ValidationResultDomain, MotsiError> {
//        return apiResponseHandler.requestMapped(
//            call = { remoteDataSource.validatePassword(ValidationRequest(password)) },
//            mapper = validationConverter::toDomain
//        )
//    }
//
//    override suspend fun validatePasswordMath(password: String): ResultWrapper<ValidationResultDomain, MotsiError> {
//        return apiResponseHandler.requestMapped(
//            call = { remoteDataSource.validatePasswordMath(ValidationRequest(password)) },
//            mapper = validationConverter::toDomain
//        )
//    }
//
//    override suspend fun registerUser(
//        username: String,
//        email: String,
//        password: String
//    ): ResultWrapper<RegistrationResult, MotsiError> {
//        return apiResponseHandler.requestMapped(
//            call = { remoteDataSource.registerUser(RegisterRequest(username, email, password)) },
//            mapper = registrationConverter::toDomain
//        )
//    }
//}
