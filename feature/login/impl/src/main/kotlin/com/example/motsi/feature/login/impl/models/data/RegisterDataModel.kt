package com.example.motsi.feature.login.impl.models.data

import kotlinx.serialization.SerialName

data class UsernameRequest(
    @SerialName("username")
    val username: String
)

data class EmailRequest(
    @SerialName("email")
    val email: String
)

data class PasswordRequest(
    @SerialName("password")
    val password: String
)

data class RegisterRequest(
    @SerialName("username")
    val username: String,

    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,
)


data class ValidationUsernameDataModel(
    @SerialName("errorMessage")
    val errorMessage: String? = null
)

data class ValidationEmailDataModel(
    @SerialName("errorMessage")
    val errorMessage: String? = null
)

data class ValidationPasswordDataModel(
    @SerialName("errorMessage")
    val errorMessage: String? = null
)

data class RegisterDataModel(
    @SerialName("isSuccess")
    val isSuccess: Boolean,

    @SerialName("userId")
    val userId: Long? = null,
)