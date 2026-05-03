package com.example.motsi.feature.search.impl.models.presentation.screen

import com.example.motsi.feature.search.impl.models.domain.SearchTip
import kotlinx.collections.immutable.ImmutableList


internal sealed class SearchScreenIntent {
    data class ClickSearchField(
        val searchQuery: String,
        val searchHint: String,
        val historyTipList: ImmutableList<SearchTip>
    ) : SearchScreenIntent()

    data object ChangeScreenStateToMapAndList: SearchScreenIntent()
    data object ChangeScreenStateToList: SearchScreenIntent()
    data object ChangeScreenStateToMap: SearchScreenIntent()
}