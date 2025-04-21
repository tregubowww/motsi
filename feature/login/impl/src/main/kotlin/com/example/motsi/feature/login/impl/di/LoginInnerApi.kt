package com.example.motsi.feature.login.impl.di

import com.example.motsi.feature.login.api.di.LoginApi
import com.example.motsi.feature.login.impl.presentation.LoginViewModel

internal interface LoginInnerApi : LoginApi{
    val viewModel: LoginViewModel
}