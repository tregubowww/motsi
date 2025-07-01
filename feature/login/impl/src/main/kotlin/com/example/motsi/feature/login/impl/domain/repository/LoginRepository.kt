package com.example.motsi.feature.login.impl.domain.repository

import com.example.motsi.feature.login.impl.data.repository.FieldType

internal interface LoginRepository {

    suspend fun validateFields(
        username: String,
        email: String,
        password1: String,
        password2: String
    ): Map<FieldType, String?>


    suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): Boolean
}