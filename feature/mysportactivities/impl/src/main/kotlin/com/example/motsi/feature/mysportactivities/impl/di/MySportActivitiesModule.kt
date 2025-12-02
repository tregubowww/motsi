package com.example.motsi.feature.mysportactivities.impl.di

import com.example.motsi.core.di.FeatureScope
import com.example.motsi.feature.mysportactivities.impl.domain.interactor.MySportActivitiesInteractor
import com.example.motsi.feature.mysportactivities.impl.domain.interactor.MySportActivitiesInteractorImpl
import dagger.Module
import dagger.Provides


@Module
internal class MySportActivitiesModule {

    @FeatureScope
    @Provides
    fun provideSearchInteractor(): MySportActivitiesInteractor =
        MySportActivitiesInteractorImpl()
}