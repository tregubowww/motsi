package com.example.motsi.feature.login.impl.data.networkservice

import com.example.motsi.feature.login.impl.models.data.EmailRequest
import com.example.motsi.feature.login.impl.models.data.PasswordRequest
import com.example.motsi.feature.login.impl.models.data.RegisterDataModel
import com.example.motsi.feature.login.impl.models.data.RegisterRequest
import com.example.motsi.feature.login.impl.models.data.UsernameRequest
import com.example.motsi.feature.login.impl.models.data.ValidationEmailDataModel
import com.example.motsi.feature.login.impl.models.data.ValidationPasswordDataModel
import com.example.motsi.feature.login.impl.models.data.ValidationUsernameDataModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRemoteDataSource {

    @POST("validate/username")
    suspend fun validateUsername(@Body request: UsernameRequest): Response<ValidationUsernameDataModel>

    @POST("validate/email")
    suspend fun validateEmail(@Body request: EmailRequest): Response<ValidationEmailDataModel>

    @POST("validate/password")
    suspend fun validatePassword(@Body request: PasswordRequest): Response<ValidationPasswordDataModel>

    @POST("register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterDataModel>
}