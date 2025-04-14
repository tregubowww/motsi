package com.example.motsi.ui.compose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.motsi.di.DaggerAppComponent
import com.example.motsi.di.SplashGraph


@Composable
internal fun Navigation() {
    val navController = rememberNavController()
    val api = DaggerAppComponent.create()
    val bottomNavBar: @Composable () -> Unit = { BottomNavBar(navController) }
    NavHost(navController = navController, startDestination = SplashGraph) {
        api.splashScreenLauncher.apply { navigationGraph(navController) }
        api.searchLauncher.apply { navigationGraph(navController, bottomNavBar) }
        api.messagesLauncher.apply { navigationGraph(navController, bottomNavBar) }
        api.activityDetailsLauncher.apply { navigationGraph(navController) }
    }
}