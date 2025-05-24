package com.example.motsi.feature.login.impl.di

import com.example.motsi.feature.login.api.di.LoginApi
import com.example.motsi.feature.login.impl.presentation.view_model.AuthViewModel
import com.example.motsi.feature.login.impl.presentation.view_model.RegisterViewModel

internal interface LoginInnerApi : LoginApi{
    val authViewModel: AuthViewModel
    val registerViewModel: RegisterViewModel
}