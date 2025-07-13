package com.example.motsi.feature.login.impl.di

import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.network.data.ApiResponseHandler
import com.example.motsi.feature.login.impl.data.converter.EmailConverter
import com.example.motsi.feature.login.impl.data.converter.PasswordConverter
import com.example.motsi.feature.login.impl.data.converter.RegistrationConverter
import com.example.motsi.feature.login.impl.data.converter.UsernameConverter
import com.example.motsi.feature.login.impl.data.networkservice.LoginRemoteDataSource
import com.example.motsi.feature.login.impl.data.repository.LoginRepositoryImpl
import com.example.motsi.feature.login.impl.domain.interactor.LoginInteractor
import com.example.motsi.feature.login.impl.domain.interactor.LoginInteractorImpl
import com.example.motsi.feature.login.impl.domain.repository.LoginRepository
import com.example.motsi.feature.login.impl.validator.StringValidator
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Inject

@Module
internal class LoginModule {

    @FeatureScope
    @Provides
    fun provideLoginRemoteDataSource(retrofit: Retrofit): LoginRemoteDataSource {
        return retrofit.create(LoginRemoteDataSource::class.java)
    }

    @FeatureScope
    @Provides
    fun provideUsernameConverter(): UsernameConverter {
        return UsernameConverter()
    }

    @FeatureScope
    @Provides
    fun provideEmailConverter(): EmailConverter {
        return EmailConverter()
    }

    @FeatureScope
    @Provides
    fun providePasswordConverter(): PasswordConverter {
        return PasswordConverter()
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
        usernameConverter: UsernameConverter,
        emailConverter: EmailConverter,
        passwordConverter: PasswordConverter,
        registrationConverter: RegistrationConverter,
        apiResponseHandler: ApiResponseHandler,
    ): LoginRepository {
        return LoginRepositoryImpl(
            remoteDataSource = remoteDataSource,
            usernameConverter = usernameConverter,
            emailConverter = emailConverter,
            passwordConverter = passwordConverter,
            registrationConverter = registrationConverter,
            apiResponseHandler = apiResponseHandler
        )
    }

    @FeatureScope
    @Provides
    fun provideLoginInteractor(loginRepository: LoginRepositoryImpl): LoginInteractor =
        LoginInteractorImpl(loginRepository)
}