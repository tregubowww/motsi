package com.example.motsi.core.common.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface FeatureLauncher {

    fun NavGraphBuilder.navigationGraph(
        navController: NavHostController,
        bottomNavBar: @Composable () -> Unit = {}
    )
}