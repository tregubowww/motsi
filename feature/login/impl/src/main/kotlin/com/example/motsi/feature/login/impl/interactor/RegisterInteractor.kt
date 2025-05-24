package com.example.motsi.feature.login.impl.interactor

import com.example.motsi.feature.login.impl.validator.StringValidator
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
            username.isEmpty() -> ValidationError.USERNAME_REQUIRED
            !validator.isValidUsername(username) -> ValidationError.INVALID_USERNAME_FORMAT
            else -> null
        }

        // Email validation
        errors[FieldType.EMAIL] = when {
            email.isEmpty() -> ValidationError.EMAIL_REQUIRED
            !validator.isValidEmail(email) -> ValidationError.INVALID_EMAIL_FORMAT
            else -> null
        }

        // Password validation
        errors[FieldType.PASSWORD1] = when {
            password1.isEmpty() -> ValidationError.PASSWORD_REQUIRED
            !validator.isStrongPassword(password1) -> ValidationError.WEAK_PASSWORD
            else -> null
        }

        errors[FieldType.PASSWORD2] = when {
            password2.isEmpty() -> ValidationError.PASSWORD_REPEAT_REQUIRED
            password1 != password2 -> ValidationError.PASSWORDS_MISMATCH
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

object ValidationError {
    const val USERNAME_REQUIRED = "username_required"
    const val INVALID_USERNAME_FORMAT = "invalid_username_format"
    const val EMAIL_REQUIRED = "email_required"
    const val INVALID_EMAIL_FORMAT = "invalid_email_format"
    const val PASSWORD_REQUIRED = "password_required"
    const val WEAK_PASSWORD = "weak_password"
    const val PASSWORD_REPEAT_REQUIRED = "password_repeat_required"
    const val PASSWORDS_MISMATCH = "passwords_mismatch"
}

object ValidationMessages {
    const val USERNAME_REQUIRED = "Имя пользователя обязательно"
    const val INVALID_USERNAME_FORMAT = "Имя должно быть от 3 до 30 символов, не содержать пробелов и специальных символов, кроме _.-"
    const val EMAIL_REQUIRED = "Email обязателен"
    const val INVALID_EMAIL_FORMAT = "Введите корректный email, например, user@example.com"
    const val PASSWORD_REQUIRED = "Пароль обязателен"
    const val WEAK_PASSWORD = "Пароль должен содержать минимум одну заглавную букву, одну строчную букву, одну цифру и спецсимвол (!@#$%^*()_+)"
    const val PASSWORD_REPEAT_REQUIRED = "Повторите пароль"
    const val PASSWORDS_MISMATCH = "Пароли не совпадают"
}