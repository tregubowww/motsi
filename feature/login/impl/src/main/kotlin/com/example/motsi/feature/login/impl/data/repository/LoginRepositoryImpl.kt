package com.example.motsi.feature.login.impl.data.repository

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.data.ApiResponseHandler
import com.example.motsi.core.network.data.requestMapped
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.login.impl.data.converter.EmailConverter
import com.example.motsi.feature.login.impl.data.converter.PasswordConverter
import com.example.motsi.feature.login.impl.data.converter.RegistrationConverter
import com.example.motsi.feature.login.impl.data.converter.UsernameConverter
import com.example.motsi.feature.login.impl.data.networkservice.LoginRemoteDataSource
import com.example.motsi.feature.login.impl.domain.repository.LoginRepository
import com.example.motsi.feature.login.impl.models.data.EmailRequest
import com.example.motsi.feature.login.impl.models.data.PasswordRequest
import com.example.motsi.feature.login.impl.models.data.RegisterRequest
import com.example.motsi.feature.login.impl.models.data.UsernameRequest
import com.example.motsi.feature.login.impl.models.domain.EmailValidationResult
import com.example.motsi.feature.login.impl.models.domain.PasswordValidationResult
import com.example.motsi.feature.login.impl.models.domain.RegistrationResult
import com.example.motsi.feature.login.impl.models.domain.UsernameValidationResult
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val remoteDataSource: LoginRemoteDataSource,
    private val usernameConverter: UsernameConverter,
    private val emailConverter: EmailConverter,
    private val passwordConverter: PasswordConverter,
    private val registrationConverter: RegistrationConverter,
    private val apiResponseHandler: ApiResponseHandler,
) : LoginRepository {

    override suspend fun validateUsername(username: String): ResultWrapper<UsernameValidationResult, MotsiError> =
        apiResponseHandler.requestMapped(
            call = { remoteDataSource.validateUsername(UsernameRequest(username)) },
            mapper = usernameConverter::toDomain
        )

    override suspend fun validateEmail(email: String): ResultWrapper<EmailValidationResult, MotsiError> =
        apiResponseHandler.requestMapped(
            call = { remoteDataSource.validateEmail(EmailRequest(email)) },
            mapper = emailConverter::toDomain
        )

    override suspend fun validatePassword(password: String): ResultWrapper<PasswordValidationResult, MotsiError> =
        apiResponseHandler.requestMapped(
            call = { remoteDataSource.validatePassword(PasswordRequest(password)) },
            mapper = passwordConverter::toDomain
        )

    override suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): ResultWrapper<RegistrationResult, MotsiError> =
        apiResponseHandler.requestMapped(
            call = { remoteDataSource.registerUser(RegisterRequest(username, email, password)) },
            mapper = registrationConverter::toDomain
        )
}

/** Рабочая версия без использования API */
//    class LoginRepositoryImpl @Inject constructor(
//        private val validator: StringValidator
//    ) : LoginRepository {
//
//        override suspend fun validateUsername(username: String): String? {
//            return when {
//                username.isEmpty() -> "Имя пользователя обязательно"
//                else -> validator.validateUsername(username)
//            }
//        }
//
//
//        override suspend fun validateEmail(email: String): String? {
//            return when {
//                email.isEmpty() -> "Email обязателен"
//                else -> validator.validateEmail(email)
//            }
//        }
//
//        override suspend fun validatePassword(password: String): String? {
//            return when {
//                password.isEmpty() -> "Пароль обязателен"
//                else -> validator.validatePassword(password)
//            }
//        }
//
//        override suspend fun validatePasswordMatch(
//            password1: String,
//            password2: String
//        ): String? {
//            return when {
//                password2.isEmpty() -> "Повторите пароль"
//                password1 != password2 -> "Пароли не совпадают"
//                else -> null
//            }
//        }
//
//        override suspend fun registerUser(
//            username: String,
//            email: String,
//            password: String
//        ) {
//            // Реальный вызов API
//        }
//    }