package com.example.motsi.feature.login.impl.data.networkservice

import com.example.motsi.feature.login.impl.models.data.RegisterRequest
import com.example.motsi.feature.login.impl.models.data.RegisterResponse
import com.example.motsi.feature.login.impl.models.data.RegisterScreenDataModel
import com.example.motsi.feature.login.impl.models.data.ValidationRequest
import com.example.motsi.feature.login.impl.models.data.ValidationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface LoginRemoteDataSource {

    @GET("register/screen")
    suspend fun getRegisterScreen(): Response<RegisterScreenDataModel>

    @POST("validate/username")
    suspend fun validateUsername(@Body request: ValidationRequest): Response<ValidationResponse>

    @POST("validate/email")
    suspend fun validateEmail(@Body request: ValidationRequest): Response<ValidationResponse>

    @POST("validate/password")
    suspend fun validatePassword(@Body request: ValidationRequest): Response<ValidationResponse>

    @POST("validate/password/math")
    suspend fun validatePasswordMath(@Body request: ValidationRequest): Response<ValidationResponse>

    @POST("register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>
}