package com.example.motsi.messeges.impl.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.di.viewmodel.ViewModelFactory
import com.example.motsi.core.di.viewmodel.ViewModelKey
import com.example.motsi.messeges.impl.presentation.MessagesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface MessagesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MessagesViewModel::class)
    fun bindMessagesViewModel(vm: MessagesViewModel): ViewModel

    @FeatureScope
    @Binds
    fun bindFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}