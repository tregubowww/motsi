package com.example.motsi.core.network.di

import com.example.motsi.core.network.data.ApiResponseHandler
import retrofit2.Retrofit

interface NetworkApi  {
    val retrofit: Retrofit
    val apiResponseHandler: ApiResponseHandler
}