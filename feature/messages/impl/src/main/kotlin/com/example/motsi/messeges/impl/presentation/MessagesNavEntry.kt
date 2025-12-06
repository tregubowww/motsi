package com.example.motsi.messeges.impl.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.motsi.core.di.holder.getFeatureApi
import com.example.motsi.core.navigation.presentation.FeatureNavEntry
import com.example.motsi.core.navigation.presentation.featureEntry
import com.example.motsi.messages.api.MessagesGraph
import com.example.motsi.messeges.impl.di.MessagesHolder
import com.example.motsi.messeges.impl.di.MessagesInternalApi
import com.example.motsi.messeges.impl.models.presentation.MessagesDestination
import com.example.motsi.messeges.impl.presentation.compose.MessagesScreen
import javax.inject.Inject

class MessagesNavEntry @Inject constructor() : FeatureNavEntry {

    override fun NavGraphBuilder.register(
        bottomNavBar: @Composable () -> Unit
    ) {

        featureEntry<MessagesInternalApi>(MessagesHolder) {
            navigation<MessagesGraph>(startDestination = MessagesDestination) {
                val api = getFeatureApi<MessagesInternalApi>()
                val factory = api.viewModelFactory()

                composable<MessagesDestination> {
                    val viewModel: MessagesViewModel = viewModel(factory = factory)

                    MessagesScreen(
                        viewModel = viewModel,
                        bottomNavBar = bottomNavBar
                    )
                }
            }
        }
    }
}