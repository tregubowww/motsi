package com.example.motsi.feature.userprofile.impl.di

import com.example.motsi.core.di.FeatureScope
import com.example.motsi.feature.userprofile.impl.domain.interactor.UserProfileInteractor
import com.example.motsi.feature.userprofile.impl.domain.interactor.UserProfileInteractorImpl
import dagger.Module
import dagger.Provides

@Module
internal class UserProfileModule {

    @FeatureScope
    @Provides
    fun provideUserProfileInteractor(): UserProfileInteractor =
        UserProfileInteractorImpl()
}