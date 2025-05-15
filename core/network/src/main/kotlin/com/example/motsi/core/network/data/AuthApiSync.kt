package com.example.motsi.core.network.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiSync {
    @POST("auth/refresh")
    fun refresh(@Body body: RefreshTokenRequest): Call<RefreshTokenResponse>
}