package com.example.motsi.messeges.impl.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.motsi.core.common.presentation.utils.injectedViewModel
import com.example.motsi.core.navigation.presentation.FeatureNavEntry
import com.example.motsi.messages.api.MessagesGraph
import com.example.motsi.messeges.impl.di.DaggerMessagesComponent
import com.example.motsi.messeges.impl.models.presentation.MessagesDestination
import com.example.motsi.messeges.impl.presentation.compose.MessagesScreen
import javax.inject.Inject

class MessagesLauncher @Inject constructor() : FeatureNavEntry {

    override fun NavGraphBuilder.register(
//        navController: NavHostController,
        bottomNavBar: @Composable () -> Unit
    ) {

        navigation<MessagesGraph>(startDestination = MessagesDestination) {
            val api = DaggerMessagesComponent.builder().build()

            composable<MessagesDestination> {

            }
        }
    }
}