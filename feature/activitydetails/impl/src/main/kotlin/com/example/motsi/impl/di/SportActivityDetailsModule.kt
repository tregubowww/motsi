package com.example.motsi.impl.di

import com.example.motsi.core.di.FeatureScope
import com.example.motsi.impl.domain.interactor.SportActivityDetailsInteractor
import com.example.motsi.impl.domain.interactor.SportActivityDetailsInteractorImpl
import dagger.Module
import dagger.Provides


@Module
internal class SportActivityDetailsModule
{
    @FeatureScope
    @Provides
    fun provideSportActivityDetailsInteractor(): SportActivityDetailsInteractor =
        SportActivityDetailsInteractorImpl()
}