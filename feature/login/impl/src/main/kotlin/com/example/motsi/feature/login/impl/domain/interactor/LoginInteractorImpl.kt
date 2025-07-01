package com.example.motsi.feature.login.impl.domain.interactor

import com.example.motsi.feature.login.impl.data.repository.FieldType
import com.example.motsi.feature.login.impl.domain.repository.LoginRepository
import javax.inject.Inject

internal class LoginInteractorImpl @Inject constructor(
    private val repository: LoginRepository
) : LoginInteractor {

    override suspend fun validateFields(
        username: String,
        email: String,
        password1: String,
        password2: String
    ): Map<FieldType, String?> = repository.validateFields(username, email, password1, password2)

    override suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): Boolean = repository.registerUser(username, email, password)
}





