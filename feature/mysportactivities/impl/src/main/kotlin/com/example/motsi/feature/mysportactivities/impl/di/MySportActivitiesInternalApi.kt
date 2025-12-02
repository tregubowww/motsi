package com.example.motsi.feature.mysportactivities.impl.di

import androidx.lifecycle.ViewModelProvider
import com.example.motsi.feature.mysportactivities.api.di.MySportActivitiesApi


internal interface MySportActivitiesInternalApi : MySportActivitiesApi {
    fun viewModelFactory(): ViewModelProvider.Factory
}