package com.example.motsi.feature.login.impl.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.motsi.core.common.presentation.injectedViewModel
import com.example.motsi.feature.login.api.LoginGraph
import com.example.motsi.feature.login.impl.di.DaggerLoginComponent
import com.example.motsi.feature.login.impl.models.presentation.EmailCodeScreenDestination
import com.example.motsi.feature.login.impl.models.presentation.LoginScreenDestination
import com.example.motsi.feature.login.impl.models.presentation.RegisterScreenDestination
import com.example.motsi.feature.login.impl.models.presentation.MainLoginScreenDestination
import com.example.motsi.feature.login.impl.presentation.compose.EmailCodeScreen
import com.example.motsi.feature.login.impl.presentation.compose.LoginScreen
import com.example.motsi.feature.login.impl.presentation.compose.RegisterScreen
import com.example.motsi.feature.login.impl.presentation.compose.MainLoginScreen
import jakarta.inject.Inject

class LoginLauncher @Inject constructor() :
    com.example.motsi.core.common.presentation.navigation.FeatureLauncher {

    override fun NavGraphBuilder.navigationGraph(
        navController: NavHostController,
        bottomNavBar: @Composable () -> Unit
    ) {
        navigation<LoginGraph>(startDestination = MainLoginScreenDestination) {
            val api = DaggerLoginComponent.builder().build()

            composable<MainLoginScreenDestination> {
                MainLoginScreen(
                    onInverseClicked = {
                        navController.navigate(RegisterScreenDestination)
                    },
                    onBrandClicked = {
                        navController.navigate(LoginScreenDestination)
                    }
                )
            }

            composable<RegisterScreenDestination> {
                val viewModel = injectedViewModel { api.viewModel }
                RegisterScreen(
                    viewModel = viewModel,
                    onBackPressed = { navController.popBackStack() },
                    onRegisterClicked = {
                        navController.navigate(EmailCodeScreenDestination)
                    }
                )
            }

            composable<LoginScreenDestination> {
                val viewModel = injectedViewModel { api.viewModel }
                LoginScreen(
                    viewModel = viewModel,
                    onBackPressed = { navController.popBackStack() }
                )
            }

            composable<EmailCodeScreenDestination> {
                val viewModel = injectedViewModel { api.viewModel }
                EmailCodeScreen(
                    viewModel = viewModel,
                    onBackPressed = { navController.popBackStack() }
                )
            }
        }
    }
}