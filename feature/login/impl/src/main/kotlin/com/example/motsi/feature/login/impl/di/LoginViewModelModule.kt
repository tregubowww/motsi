package com.example.motsi.feature.login.impl.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.di.viewmodel.ViewModelFactory
import com.example.motsi.core.di.viewmodel.ViewModelKey
import com.example.motsi.feature.login.impl.presentation.view_model.AuthViewModel
import com.example.motsi.feature.login.impl.presentation.view_model.RegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface LoginViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    fun bindRegisterViewModel(vm: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun bindAuthViewModel(vm: AuthViewModel): ViewModel

    @FeatureScope
    @Binds
    fun bindFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}