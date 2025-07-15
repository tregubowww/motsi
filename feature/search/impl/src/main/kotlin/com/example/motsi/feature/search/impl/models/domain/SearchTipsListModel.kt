package com.example.motsi.feature.search.impl.models.domain

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class SearchTipsListModel(
    val tipsList: ImmutableList<SearchTipItem> = persistentListOf()
) {
    data class SearchTipItem(
        val tips: String,
        val tipsCategory: String?,
        val isInSearchHistory: Boolean
    )
}