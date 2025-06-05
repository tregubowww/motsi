package com.example.motsi.core.navigation.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder

interface FeatureNavEntry {

    fun NavGraphBuilder.register(
        bottomNavBar: @Composable () -> Unit = {}
    )
}

