package com.example.motsi.feature.search.impl.models.presentation.screen

import androidx.navigation.NavHostController
import com.example.motsi.feature.search.impl.models.domain.SearchSportActivityTip
import kotlinx.collections.immutable.ImmutableList


internal sealed class SearchScreenIntent {
    data class ClickSearchField(
        val navController: NavHostController,
        val searchQuery: String,
        val searchHint: String,
        val historyTipList: ImmutableList<SearchSportActivityTip>
    ) : SearchScreenIntent()
}