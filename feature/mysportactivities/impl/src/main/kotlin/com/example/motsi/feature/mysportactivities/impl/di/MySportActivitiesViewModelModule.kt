package com.example.motsi.feature.mysportactivities.impl.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.di.viewmodel.ViewModelFactory
import com.example.motsi.core.di.viewmodel.ViewModelKey
import com.example.motsi.feature.mysportactivities.impl.presentation.MySportActivitiesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface MySportActivitiesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MySportActivitiesViewModel::class)
    fun bindMySportActivitiesViewModel(vm: MySportActivitiesViewModel): ViewModel


    @FeatureScope
    @Binds
    fun bindFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

