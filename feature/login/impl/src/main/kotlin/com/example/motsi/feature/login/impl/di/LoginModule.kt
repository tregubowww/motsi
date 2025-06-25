package com.example.motsi.feature.login.impl.di

import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.network.data.ApiResponseHandler
import com.example.motsi.feature.login.impl.data.converter.RegisterScreenConverter
import com.example.motsi.feature.login.impl.data.converter.RegistrationConverter
import com.example.motsi.feature.login.impl.data.converter.ValidationConverter
import com.example.motsi.feature.login.impl.data.networkservice.LoginRemoteDataSource
import com.example.motsi.feature.login.impl.data.repository.LoginRepositoryImpl
import com.example.motsi.feature.login.impl.domain.interactor.LoginInteractor
import com.example.motsi.feature.login.impl.domain.interactor.LoginInteractorImpl
import com.example.motsi.feature.login.impl.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
internal class LoginModule {

    @FeatureScope
    @Provides
    fun provideLoginRemoteDataSource(retrofit: Retrofit): LoginRemoteDataSource {
        return retrofit.create(LoginRemoteDataSource::class.java)
    }

    @FeatureScope
    @Provides
    fun provideRegisterScreenConverter(): RegisterScreenConverter {
        return RegisterScreenConverter()
    }

    @FeatureScope
    @Provides
    fun provideValidationConverter(): ValidationConverter {
        return ValidationConverter()
    }

    @FeatureScope
    @Provides
    fun provideRegistrationConverter(): RegistrationConverter {
        return RegistrationConverter()
    }

    @FeatureScope
    @Provides
    fun provideLoginRepository(
        remoteDataSource: LoginRemoteDataSource,
        registerScreenConverter: RegisterScreenConverter,
        validationConverter: ValidationConverter,
        registrationConverter: RegistrationConverter,
        apiResponseHandler: ApiResponseHandler
    ): LoginRepository {
        return LoginRepositoryImpl(
            remoteDataSource = remoteDataSource,
            registerScreenConverter = registerScreenConverter,
            validationConverter = validationConverter,
            registrationConverter = registrationConverter,
            apiResponseHandler = apiResponseHandler
        )
    }

    @FeatureScope
    @Provides
    fun provideLoginInteractor(loginRepository: LoginRepository): LoginInteractor =
        LoginInteractorImpl(loginRepository)
}