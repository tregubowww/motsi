package com.example.motsi.feature.login.impl.di

import androidx.lifecycle.ViewModelProvider
import com.example.motsi.feature.login.api.di.LoginApi
import com.example.motsi.feature.login.impl.presentation.LoginClickHandler

internal interface LoginInternalApi
    : LoginApi
{
    fun viewModelFactory(): ViewModelProvider.Factory
    fun clickHandler(): LoginClickHandler
}