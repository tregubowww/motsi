package com.example.motsi.core.wizard.impl.di

import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.wizard.impl.interactor.WizardInteractor
import com.example.motsi.core.wizard.impl.interactor.WizardInteractorImpl
import dagger.Module
import dagger.Provides


@Module
internal class WizardModule {

    @FeatureScope
    @Provides
    fun provideWizardInteractor(): WizardInteractor =
        WizardInteractorImpl()
}