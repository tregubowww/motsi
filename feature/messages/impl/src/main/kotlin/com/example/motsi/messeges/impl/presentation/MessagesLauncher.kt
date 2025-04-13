package com.example.motsi.messeges.impl.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.motsi.core.common.presentation.injectedViewModel
import com.example.motsi.messages.api.MessagesGraph
import com.example.motsi.messeges.impl.di.DaggerMessagesComponent
import com.example.motsi.messeges.impl.models.presentation.MessagesDestination
import com.example.motsi.messeges.impl.presentation.compose.MessagesScreen
import jakarta.inject.Inject

class MessagesLauncher @Inject constructor() :
    com.example.motsi.core.common.presentation.navigation.FeatureLauncher {

    override fun NavGraphBuilder.navigationGraph(
        navController: NavHostController,
        bottomNavBar: @Composable () -> Unit
    ) {

        navigation<MessagesGraph>(startDestination = MessagesDestination) {
            val api = DaggerMessagesComponent.builder().build()

            composable<MessagesDestination> {
                val viewModel = injectedViewModel { api.viewModel }
                MessagesScreen(
                    viewModel = viewModel,
                    bottomNavBar
                )
            }
        }
    }
}