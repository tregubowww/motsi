package com.example.motsi.feature.login.impl.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.motsi.core.di.holder.getFeatureApi
import com.example.motsi.core.navigation.presentation.FeatureNavEntry
import com.example.motsi.core.navigation.presentation.featureEntry
import com.example.motsi.feature.login.api.LoginGraph
import com.example.motsi.feature.login.impl.di.LoginHolder
import com.example.motsi.feature.login.impl.di.LoginInternalApi
import com.example.motsi.feature.login.impl.models.presentation.EmailCodeScreenDestination
import com.example.motsi.feature.login.impl.models.presentation.AuthScreenDestination
import com.example.motsi.feature.login.impl.models.presentation.RegisterScreenDestination
import com.example.motsi.feature.login.impl.models.presentation.MainLoginScreenDestination
import com.example.motsi.feature.login.impl.presentation.compose.AuthScreen
import com.example.motsi.feature.login.impl.presentation.compose.EmailCodeScreen
import com.example.motsi.feature.login.impl.presentation.compose.RegisterScreen
import com.example.motsi.feature.login.impl.presentation.compose.MainLoginScreen
import com.example.motsi.feature.login.impl.presentation.view_model.AuthViewModel
import com.example.motsi.feature.login.impl.presentation.view_model.RegisterViewModel
import jakarta.inject.Inject

class LoginNavEntry @Inject constructor() : FeatureNavEntry {

    override fun NavGraphBuilder.register(
        bottomNavBar: @Composable () -> Unit
    ) {
        featureEntry<LoginInternalApi>(LoginHolder) {
            navigation<LoginGraph>(startDestination = MainLoginScreenDestination) {
                val api = getFeatureApi<LoginInternalApi>()
                val factory = api.viewModelFactory()
                val clickHandler = api.clickHandler()


                composable<MainLoginScreenDestination> {
                    MainLoginScreen(
                        clickHandler = clickHandler
                    )
                }

                composable<RegisterScreenDestination> {
                    val viewModel: RegisterViewModel = viewModel(factory = factory)
                    RegisterScreen(
                        viewModel = viewModel,
                        clickHandler = clickHandler
                    )
                }

                composable<AuthScreenDestination> {
                    val viewModel: AuthViewModel = viewModel(factory = factory)
                    AuthScreen(
                        viewModel = viewModel,
                        clickHandler = clickHandler
                    )
                }

                composable<EmailCodeScreenDestination> {
                    val viewModel: AuthViewModel =
                        viewModel(factory = factory) //заменить на другой viewModel
                    EmailCodeScreen(
                        viewModel = viewModel,
                        clickHandler = clickHandler
                    )
                }
            }
        }
    }
}