package com.example.motsi.feature.addsportactivity.impl.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.di.viewmodel.ViewModelFactory
import com.example.motsi.core.di.viewmodel.ViewModelKey
import com.example.motsi.feature.addsportactivity.impl.presentation.compose.AddSportActivityViewModel
import com.example.motsi.feature.addsportactivity.impl.presentation.choicetypesport.ChoiceTypeSportViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface AddSportActivityViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddSportActivityViewModel::class)
    fun bindAddSportActivityViewModel(vm: AddSportActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChoiceTypeSportViewModel::class)
    fun bindChoiceTypeSportViewModel(vm: ChoiceTypeSportViewModel): ViewModel

    @FeatureScope
    @Binds
    fun bindFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

