package com.example.motsi.core.wizard.impl.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.di.viewmodel.ViewModelFactory
import com.example.motsi.core.di.viewmodel.ViewModelKey
import com.example.motsi.core.wizard.impl.presentation.WizardCoordinatorViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface WizardViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WizardCoordinatorViewModel::class)
    fun bindWizardScreenViewModel(vm: WizardCoordinatorViewModel): ViewModel

    @FeatureScope
    @Binds
    fun bindFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

