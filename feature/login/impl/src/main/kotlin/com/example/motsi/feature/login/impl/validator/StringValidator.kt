package com.example.motsi.feature.login.impl.validator

import javax.inject.Inject

class StringValidator @Inject constructor() {
    fun isValidUsername(username: String): Boolean {
        val regex = Regex("^[\\p{L}0-9_.-]{2,30}$")
        return regex.matches(username)
    }

    fun isValidEmail(email: String): Boolean {
        val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return regex.matches(email)
    }

    fun isStrongPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#\$%^*()_+]).{8,}$")
        return regex.matches(password)
    }
}