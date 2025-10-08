package com.example.motsi.feature.addsportactivity.impl.di

import androidx.lifecycle.ViewModelProvider
import com.example.motsi.feature.addsportactivity.api.di.AddSportActivityApi


internal interface AddSportActivityInternalApi : AddSportActivityApi {
    fun viewModelFactory(): ViewModelProvider.Factory
}