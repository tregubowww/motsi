package com.example.motsi.feature.userprofile.impl.di

import androidx.lifecycle.ViewModelProvider
import com.example.motsi.feature.userprofile.api.di.UserProfileApi

internal interface UserProfileInternalApi : UserProfileApi {
    fun viewModelFactory(): ViewModelProvider.Factory
}