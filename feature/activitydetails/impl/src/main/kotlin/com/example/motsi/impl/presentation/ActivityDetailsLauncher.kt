package com.example.motsi.impl.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.motsi.api.ActivityDetailsGraph
import com.example.motsi.core.common.presentation.injectedViewModel
import com.example.motsi.core.common.presentation.navigation.toRouteGraph
import com.example.motsi.impl.di.DaggerActivityDetailsComponent
import com.example.motsi.impl.models.presentation.ActivityDetailsScreenDestination
import com.example.motsi.impl.presentation.compose.ActivityDetailsScreen
import jakarta.inject.Inject

class ActivityDetailsLauncher @Inject constructor() :
    com.example.motsi.core.common.presentation.navigation.FeatureLauncher {

    override fun NavGraphBuilder.navigationGraph(
        navController: NavHostController,
        bottomNavBar: @Composable () -> Unit
    ) {
        navigation<ActivityDetailsGraph>(startDestination = ActivityDetailsScreenDestination) {
            composable<ActivityDetailsScreenDestination> { entry ->
                val activityDetailsGraph = entry.toRouteGraph<ActivityDetailsGraph>()
                val viewModel = injectedViewModel {
                    DaggerActivityDetailsComponent.builder()
                        .build()
                        .viewModel.apply { initVM(id = activityDetailsGraph.id) }
                }
                ActivityDetailsScreen(viewModel)
            }
        }
    }
}
