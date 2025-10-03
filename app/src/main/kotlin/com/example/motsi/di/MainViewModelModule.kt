package com.example.motsi.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.motsi.core.navigation.presentation.SharedSplashScreenViewModel
import com.example.motsi.core.di.viewmodel.ViewModelFactory
import com.example.motsi.core.di.viewmodel.ViewModelKey
import com.example.motsi.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SharedSplashScreenViewModel::class)
    fun bindSplashVM(vm: SharedSplashScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainVM(vm: MainViewModel): ViewModel

    @Binds
    fun bindFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

