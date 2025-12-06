package com.example.motsi.feature.search.impl.models.presentation.tips

import androidx.navigation.NavHostController

internal sealed interface SearchTipListEffect {
    data class NavigateToSearchScreenWithNewData(
        val navController: NavHostController, val type: String?, val value: String
    ) : SearchTipListEffect

    data class NavigateToBackStack(val navController: NavHostController) : SearchTipListEffect
}