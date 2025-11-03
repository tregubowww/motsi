package com.example.motsi.feature.search.impl.models.presentation.screen

import com.example.motsi.core.ui.models.DataSnackbar
import com.example.motsi.feature.search.impl.models.presentation.SearchTipsDestination


internal sealed interface SearchScreenEffect {
    data class NavigateToSearchTips(val entryData: SearchTipsDestination.EntryData) : SearchScreenEffect

    data class NavigateToActivityDetails(val activityId: String) : SearchScreenEffect

    data class ShowSnackbar(val dataSnackbar: DataSnackbar) : SearchScreenEffect
}