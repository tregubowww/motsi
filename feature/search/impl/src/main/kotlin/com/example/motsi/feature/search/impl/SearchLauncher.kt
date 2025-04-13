package com.example.motsi.feature.search.impl

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.motsi.core.common.presentation.injectedViewModel
import com.example.motsi.feature.search.api.SearchGraph
import com.example.motsi.feature.search.impl.di.DaggerSearchComponent
import com.example.motsi.feature.search.impl.models.presentation.SearchDestination
import com.example.motsi.feature.search.impl.presentation.compose.SearchScreen
import jakarta.inject.Inject

class SearchLauncher @Inject constructor() :
    com.example.motsi.core.common.presentation.navigation.FeatureLauncher {

    override fun NavGraphBuilder.navigationGraph(
        navController: NavHostController,
        bottomNavBar: @Composable () -> Unit
    ) {

        navigation<SearchGraph>(startDestination = SearchDestination) {
            val api = DaggerSearchComponent.builder().build()

            composable<SearchDestination> {
                val viewModel = injectedViewModel { api.viewModel }
                SearchScreen(
                    viewModel = viewModel,
                    clickHandler = api.clickHandler.create(navController),
                    bottomNavBar
                )
            }
        }
    }
}