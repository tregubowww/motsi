package com.example.motsi.di

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.motsi.core.common.presentation.navigation.FeatureLauncher
import com.example.motsi.ui.compose.SplashScreen
import jakarta.inject.Inject

class SplashScreenLauncher @Inject constructor() : FeatureLauncher {

    override fun NavGraphBuilder.navigationGraph(
        navController: NavHostController,
        bottomNavBar: @Composable () -> Unit
    ) {
        navigation<SplashGraph>(
            startDestination = SplashDestination
        ) {
            composable<SplashDestination> {
                SplashScreen(navController)
            }
        }
    }
}