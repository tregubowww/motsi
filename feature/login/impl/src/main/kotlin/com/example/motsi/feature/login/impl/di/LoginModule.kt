package com.example.motsi.feature.login.impl.di

import com.example.motsi.core.di.FeatureScope
import com.example.motsi.feature.login.impl.data.networkservice.LoginRemoteDataSource
import com.example.motsi.feature.login.impl.data.repository.LoginRepositoryImpl
import com.example.motsi.feature.login.impl.domain.interactor.LoginInteractor
import com.example.motsi.feature.login.impl.domain.interactor.LoginInteractorImpl
import com.example.motsi.feature.login.impl.domain.repository.LoginRepository
import com.example.motsi.feature.login.impl.validator.MockBackendValidator
import com.example.motsi.feature.login.impl.validator.StringValidator
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

//    @FeatureScope
//    @Provides
//    fun provideRegisterScreenConverter(): RegisterScreenConverter {
//        return RegisterScreenConverter()
//    }

//    @FeatureScope
//    @Provides
//    fun provideValidationConverter(): ValidationConverter {
//        return ValidationConverter()
//    }
//
//    @FeatureScope
//    @Provides
//    fun provideRegistrationConverter(): RegistrationConverter {
//        return RegistrationConverter()
//    }

    fun provideMockBackendValidator(): MockBackendValidator {
        return MockBackendValidator()
    }

    @FeatureScope
    @Provides
    fun provideLoginRepository(
        validator: StringValidator
//        remoteDataSource: LoginRemoteDataSource,
//        registerScreenConverter: RegisterScreenConverter,
//        validationConverter: ValidationConverter,
//        registrationConverter: RegistrationConverter,
//        apiResponseHandler: ApiResponseHandler
    ): LoginRepository {
        return LoginRepositoryImpl(
            validator = validator
//            remoteDataSource = remoteDataSource,
//            registerScreenConverter = registerScreenConverter,
//            validationConverter = validationConverter,
//            registrationConverter = registrationConverter,
//            apiResponseHandler = apiResponseHandler
        )
    }

    @FeatureScope
    @Provides
    fun provideLoginInteractor(loginRepository: LoginRepositoryImpl): LoginInteractor =
        LoginInteractorImpl(loginRepository)
}