package com.example.motsi.feature.login.impl.interactor

import com.example.motsi.feature.login.impl.validator.StringValidator
import com.example.motsi.feature.login.impl.validator.ValidationMessages
import javax.inject.Inject

class RegisterInteractor @Inject constructor(
    private val validator: StringValidator
) {
    fun validateFields(
        username: String,
        email: String,
        password1: String,
        password2: String
    ): Map<FieldType, String?> {
        val errors = mutableMapOf<FieldType, String?>()

        // Username validation
        errors[FieldType.USERNAME] = when {
            username.isEmpty() -> "Имя пользователя обязательно"
            else -> {
                val validationErrors = validator.validateUsername(username)
                if (validationErrors.isNotEmpty()) {
                    ValidationMessages.getUsernameErrorMessages(validationErrors)
                } else {
                    null
                }
            }
        }

        // Email validation
        errors[FieldType.EMAIL] = when {
            email.isEmpty() -> "Email обязателен"
            else -> ValidationMessages.getEmailErrorMessage(validator.validateEmail(email))
        }

        // Password validation
        errors[FieldType.PASSWORD1] = when {
            password1.isEmpty() -> "Пароль обязателен"
            else -> {
                val validationErrors = validator.validatePassword(password1)
                if (validationErrors.isNotEmpty()) {
                    ValidationMessages.getPasswordErrorMessages(validationErrors)
                } else {
                    null
                }
            }
        }

        errors[FieldType.PASSWORD2] = when {
            password2.isEmpty() -> "Повторите пароль"
            password1 != password2 -> "Пароли не совпадают"
            else -> null
        }

        return errors
    }

    suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): Boolean {
        // Реальный вызов API
        return true // Заглушка
    }
}

enum class FieldType { USERNAME, EMAIL, PASSWORD1, PASSWORD2 }