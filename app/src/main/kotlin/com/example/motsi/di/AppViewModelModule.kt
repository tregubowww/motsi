package com.example.motsi.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.motsi.core.navigation.presentation.SharedSplashScreenViewModel
import com.example.motsi.core.di.viewmodel.ViewModelFactory
import com.example.motsi.core.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface AppViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SharedSplashScreenViewModel::class)
    fun bindSplashVM(vm: SharedSplashScreenViewModel): ViewModel

    @Binds
    fun bindFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

