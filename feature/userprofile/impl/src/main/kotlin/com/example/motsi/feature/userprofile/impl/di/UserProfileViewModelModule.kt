package com.example.motsi.feature.userprofile.impl.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.di.viewmodel.ViewModelFactory
import com.example.motsi.core.di.viewmodel.ViewModelKey
import com.example.motsi.feature.userprofile.impl.presentation.UserProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface UserProfileViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    fun bindUserProfileViewModel(vm: UserProfileViewModel): ViewModel

    @FeatureScope
    @Binds
    fun bindFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}