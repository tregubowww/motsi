package com.example.motsi.impl.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.motsi.api.SportActivityDetailsGraph
import com.example.motsi.core.di.holder.getFeatureApi
import com.example.motsi.core.navigation.presentation.FeatureNavEntry
import com.example.motsi.core.navigation.presentation.featureEntry
import com.example.motsi.core.navigation.presentation.toRouteGraph
import com.example.motsi.impl.di.SportActivityDetailsHolder
import com.example.motsi.impl.di.SportActivityDetailsInternalApi
import com.example.motsi.impl.models.presentation.ActivityDetailsScreenDestination
import com.example.motsi.impl.presentation.compose.SportActivityDetailsScreen
import javax.inject.Inject


class SportActivityDetailsNavEntry @Inject constructor() : FeatureNavEntry {

    override fun NavGraphBuilder.register(
        bottomNavBar: @Composable () -> Unit
    ) {
        featureEntry<SportActivityDetailsInternalApi>(SportActivityDetailsHolder) {
            navigation<SportActivityDetailsGraph>(startDestination = ActivityDetailsScreenDestination) {
                val factory = getFeatureApi<SportActivityDetailsInternalApi>().viewModelFactory()
                composable<ActivityDetailsScreenDestination> { entry ->
                    val args =  entry.toRouteGraph<SportActivityDetailsGraph>()
                    val viewModel: SportActivityDetailsViewModel = viewModel(factory = factory)
                    SportActivityDetailsScreen(id = args.id, viewModel = viewModel, bottomNavBar = bottomNavBar)
                }
            }
        }
    }
}
