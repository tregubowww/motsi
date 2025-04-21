package com.example.motsi.feature.login.impl.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.motsi.core.common.presentation.injectedViewModel
import com.example.motsi.feature.login.api.LoginGraph
import com.example.motsi.feature.login.impl.di.DaggerLoginComponent
import com.example.motsi.feature.login.impl.models.presentation.LoginScreenDestination
import com.example.motsi.feature.login.impl.presentation.compose.LoginScreen
import jakarta.inject.Inject

class LoginLauncher @Inject constructor() :
    com.example.motsi.core.common.presentation.navigation.FeatureLauncher {

    override fun NavGraphBuilder.navigationGraph(
        navController: NavHostController,
        bottomNavBar: @Composable () -> Unit
    ) {

        navigation<LoginGraph>(startDestination = LoginScreenDestination) {
            val api = DaggerLoginComponent.builder().build()

            composable<LoginScreenDestination> {
                val viewModel = injectedViewModel { api.viewModel }
                LoginScreen(
                    viewModel
                )
            }
        }
    }
}