package com.example.motsi.feature.search.impl.models.presentation.tips

import androidx.navigation.NavHostController


internal sealed class SearchTipListIntent {

    data class OnSearchQueryChange(
        val searchQuery: String
    ) : SearchTipListIntent()

    data class TipClick(
        val navController: NavHostController, val type: String?, val value: String
    ) : SearchTipListIntent()

    data class BackClick(
        val navController: NavHostController
    ) : SearchTipListIntent()
}