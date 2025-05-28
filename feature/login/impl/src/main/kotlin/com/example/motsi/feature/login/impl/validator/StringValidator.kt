package com.example.motsi.feature.login.impl.validator

import javax.inject.Inject

class StringValidator @Inject constructor() {
    fun validateUsername(username: String): List<UsernameValidationError> {
        val errors = mutableListOf<UsernameValidationError>()

        if (username.length < 3) errors.add(UsernameValidationError.TOO_SHORT)
        if (username.length > 30) errors.add(UsernameValidationError.TOO_LONG)
        if (username.contains(" ")) errors.add(UsernameValidationError.CONTAINS_SPACES)
        if (!Regex("^[\\p{L}0-9_.-]*$").matches(username)) {
            errors.add(UsernameValidationError.INVALID_CHARACTERS)
        }

        return errors
    }

    fun validateEmail(email: String): EmailValidationError? {
        return if (!Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").matches(email)) {
            EmailValidationError.INVALID_FORMAT
        } else {
            null
        }
    }

    fun validatePassword(password: String): List<PasswordValidationError> {
        val errors = mutableListOf<PasswordValidationError>()

        if (password.length < 8) errors.add(PasswordValidationError.TOO_SHORT)
        if (!password.any { it.isUpperCase() }) errors.add(PasswordValidationError.NO_UPPERCASE)
        if (!password.any { it.isLowerCase() }) errors.add(PasswordValidationError.NO_LOWERCASE)
        if (!password.any { it.isDigit() }) errors.add(PasswordValidationError.NO_DIGIT)
        if (!password.any { it in "!@#$%^*()_+" }) errors.add(PasswordValidationError.NO_SPECIAL_CHAR)

        return errors
    }
}

// Классы ошибок валидации
sealed class UsernameValidationError {
    object TOO_SHORT : UsernameValidationError()
    object TOO_LONG : UsernameValidationError()
    object CONTAINS_SPACES : UsernameValidationError()
    object INVALID_CHARACTERS : UsernameValidationError()
}

sealed class EmailValidationError {
    object INVALID_FORMAT : EmailValidationError()
}

sealed class PasswordValidationError {
    object TOO_SHORT : PasswordValidationError()
    object NO_UPPERCASE : PasswordValidationError()
    object NO_LOWERCASE : PasswordValidationError()
    object NO_DIGIT : PasswordValidationError()
    object NO_SPECIAL_CHAR : PasswordValidationError()
}

// Cообщения об ошибках
object ValidationMessages {
    fun getUsernameErrorMessages(errors: List<UsernameValidationError>): String {
        return errors.joinToString("\n") {
            when (it) {
                is UsernameValidationError.TOO_SHORT -> "Имя должно содержать не менее 3 символов"
                is UsernameValidationError.TOO_LONG -> "Имя должно содержать не более 30 символов"
                is UsernameValidationError.CONTAINS_SPACES -> "Имя не должно содержать пробелов"
                is UsernameValidationError.INVALID_CHARACTERS -> "Имя может содержать только буквы, цифры и символы _.-"
            }
        }
    }

    fun getEmailErrorMessage(error: EmailValidationError?): String? {
        return when (error) {
            EmailValidationError.INVALID_FORMAT -> "Введите корректный email, например, user@example.com"
            null -> null
        }
    }

    fun getPasswordErrorMessages(errors: List<PasswordValidationError>): String {
        return errors.joinToString("\n") {
            when (it) {
                is PasswordValidationError.TOO_SHORT -> "Пароль должен содержать не менее 8 символов"
                is PasswordValidationError.NO_UPPERCASE -> "Пароль должен содержать хотя бы одну заглавную букву"
                is PasswordValidationError.NO_LOWERCASE -> "Пароль должен содержать хотя бы одну строчную букву"
                is PasswordValidationError.NO_DIGIT -> "Пароль должен содержать хотя бы одну цифру"
                is PasswordValidationError.NO_SPECIAL_CHAR -> "Пароль должен содержать хотя бы один спецсимвол (!@#\$%^*()_+)"
            }
        }
    }
}