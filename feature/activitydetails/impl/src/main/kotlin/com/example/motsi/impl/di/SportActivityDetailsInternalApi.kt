package com.example.motsi.impl.di

import androidx.lifecycle.ViewModelProvider
import com.example.motsi.api.di.ActivityDetailsApi


internal interface SportActivityDetailsInternalApi : ActivityDetailsApi{
    fun viewModelFactory(): ViewModelProvider.Factory
}
