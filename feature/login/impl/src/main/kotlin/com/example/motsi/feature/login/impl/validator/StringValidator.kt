package com.example.motsi.feature.login.impl.validator

import javax.inject.Inject

class StringValidator @Inject constructor() {
    fun validateUsername(username: String): String? {
        return when {
            username.length < 3 -> "Имя должно содержать не менее 3 символов"
            username.length > 30 -> "Имя должно содержать не более 30 символов"
            username.contains(" ") -> "Имя не должно содержать пробелов"
            !Regex("^[\\p{L}0-9_.-]*$").matches(username) -> "Имя может содержать только буквы, цифры и символы _.-"
            else -> null
        }
    }

    fun validateEmail(email: String): String? {
        return if (!Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").matches(email)) {
            "Введите корректный email, например, user@example.com"
        } else {
            null
        }
    }

    fun validatePassword(password: String): String? {
        return when {
            password.length < 8 -> "Пароль должен содержать не менее 8 символов"
            !password.any { it.isUpperCase() } -> "Пароль должен содержать хотя бы одну заглавную букву"
            !password.any { it.isLowerCase() } -> "Пароль должен содержать хотя бы одну строчную букву"
            !password.any { it.isDigit() } -> "Пароль должен содержать хотя бы одну цифру"
            !password.any { it in "!@#$%^*()_+" } -> "Пароль должен содержать хотя бы один спецсимвол (!@#\$%^*()_+)"
            else -> null
        }
    }
}