package com.example.motsi.core.navigation.presentation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.motsi.core.navigation.models.Destination
import com.example.motsi.core.navigation.models.NavBottomBarItem
import com.example.motsi.core.navigation.presentation.FeatureNavEntry


val LocalAppNavController = staticCompositionLocalOf<NavHostController> {
    error("No NavController provided")
}

@Composable
fun Navigation(navEntrySet: Set<@JvmSuppressWildcards FeatureNavEntry>, startDestination: Destination, itemsBottomNavBar: List<NavBottomBarItem>) {
    val navController = rememberNavController()
    val bottomNavBar: @Composable () -> Unit = { BottomNavBar(navController, itemsBottomNavBar) }
    CompositionLocalProvider(LocalAppNavController provides navController) {
        NavHost(navController = navController, startDestination = startDestination) {
            navEntrySet.forEach { it.apply { register(bottomNavBar = bottomNavBar) } }
        }
    }
}

