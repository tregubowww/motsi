package com.example.motsi.impl.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.motsi.api.ActivityDetailsGraph
import com.example.motsi.core.common.presentation.utils.injectedViewModel
import com.example.motsi.core.navigation.presentation.FeatureNavEntry
import com.example.motsi.core.navigation.presentation.toRouteGraph
import com.example.motsi.impl.di.DaggerActivityDetailsComponent
import com.example.motsi.impl.models.presentation.ActivityDetailsScreenDestination
import com.example.motsi.impl.presentation.compose.ActivityDetailsScreen
import javax.inject.Inject

class ActivityDetailsNavEntry @Inject constructor() : FeatureNavEntry {

    override fun NavGraphBuilder.register(
//        navController: NavHostController,
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
