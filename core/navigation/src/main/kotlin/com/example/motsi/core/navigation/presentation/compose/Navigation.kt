package com.example.motsi.core.navigation.presentation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.motsi.core.navigation.models.Destination
import com.example.motsi.core.navigation.presentation.FeatureNavEntry


val LocalAppNavController = staticCompositionLocalOf<NavHostController> {
    error("No NavController provided")
}

@Composable
fun Navigation(
    navEntrySet: Set<@JvmSuppressWildcards FeatureNavEntry>,
    startDestination: Destination,
    bottomNavBar: @Composable (NavHostController) -> Unit
) {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalAppNavController provides navController) {
        NavHost(navController = navController, startDestination = startDestination) {
            navEntrySet.forEach {
                it.apply {
                    register(bottomNavBar = {
                        bottomNavBar.invoke(
                            navController
                        )
                    })
                }
            }
        }
    }
}

