package com.example.motsi.feature.addsportactivity.impl.di

import com.example.motsi.core.di.FeatureScope
import com.example.motsi.feature.addsportactivity.impl.domain.interactor.AddSportActivityInteractor
import com.example.motsi.feature.addsportactivity.impl.domain.interactor.AddSportActivityInteractorImpl
import dagger.Module
import dagger.Provides


@Module
internal class AddSportActivityModule {

    @FeatureScope
    @Provides
    fun provideSearchInteractor(): AddSportActivityInteractor =
        AddSportActivityInteractorImpl()
}