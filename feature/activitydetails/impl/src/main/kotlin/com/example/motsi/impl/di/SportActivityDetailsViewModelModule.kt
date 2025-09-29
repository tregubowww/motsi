package com.example.motsi.impl.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.di.viewmodel.ViewModelFactory
import com.example.motsi.core.di.viewmodel.ViewModelKey
import com.example.motsi.impl.presentation.SportActivityDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface SportActivityDetailsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SportActivityDetailsViewModel::class)
    fun bindSportActivityDetailsViewModel(vm: SportActivityDetailsViewModel): ViewModel

    @FeatureScope
    @Binds
    fun bindFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

