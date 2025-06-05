package com.example.motsi.core.network.di

import com.example.motsi.core.network.BuildConfig
import com.example.motsi.core.network.data.ApiResponseHandler
import com.example.motsi.core.network.data.ApiResponseHandlerImpl
import com.example.motsi.core.network.data.ErrorConverter
import com.example.motsi.core.network.domain.consts.MediaTypes
import com.example.motsi.core.wrappers.di.WrappersCoreApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import retrofit2.Retrofit

@Module
class NetworkModule {

    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideRetrofit(json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory(MediaTypes.JSON))
            .build()
    }

    @Provides
    fun provideErrorConverter(
        wrappersCoreApi: WrappersCoreApi
    ): ErrorConverter =
        ErrorConverter(
            wrappersCoreApi.resourceManager()
        )

    @Provides
    fun provideApiResponseHandler(errorConverter: ErrorConverter): ApiResponseHandler =
        ApiResponseHandlerImpl(
            errorConverter
        )
}