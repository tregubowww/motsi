package com.example.motsi.feature.search.impl.models.presentation.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import com.example.motsi.feature.search.impl.models.domain.SearchTip
import kotlinx.collections.immutable.ImmutableList


internal sealed class SearchScreenIntent {
    data class ClickSearchField(
        val searchQuery: String,
        val searchHint: String,
        val historyTipList: ImmutableList<SearchTip>
    ) : SearchScreenIntent()

    @OptIn(ExperimentalMaterial3Api::class)
    data class ChangeScreenState(val currentValue: SheetValue) : SearchScreenIntent()
}