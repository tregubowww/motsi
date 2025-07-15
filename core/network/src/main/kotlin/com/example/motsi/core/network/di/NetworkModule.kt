package com.example.motsi.core.network.di

import com.example.motsi.core.network.BuildConfig
import com.example.motsi.core.network.data.ApiResponseHandler
import com.example.motsi.core.network.data.ApiResponseHandlerImpl
import com.example.motsi.core.network.data.AuthApiSync
import com.example.motsi.core.network.data.AuthInterceptor
import com.example.motsi.core.network.data.ErrorConverter
import com.example.motsi.core.network.data.SessionManager
import com.example.motsi.core.network.data.SessionManagerImpl
import com.example.motsi.core.network.domain.consts.MediaTypes
import com.example.motsi.core.wrappers.di.WrappersCoreApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
internal class NetworkModule {

    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideRetrofit(json: Json, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(MediaTypes.JSON))
            .build()
    }

    @Provides
    fun provideAuthApiSync(retrofit: Retrofit): AuthApiSync {
        return retrofit.create(AuthApiSync::class.java)
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

    @Provides
    @Singleton
    fun provideSessionManager(
        wrappersCoreApi: WrappersCoreApi
    ): SessionManager = SessionManagerImpl(wrappersCoreApi.application())
}