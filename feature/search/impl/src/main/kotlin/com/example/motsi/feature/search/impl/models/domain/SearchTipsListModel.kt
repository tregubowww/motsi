package com.example.motsi.feature.search.impl.models.domain

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class SearchTipsListModel(
    val tipsList: ImmutableList<Item> = persistentListOf()
) {
    data class Item(
        val tip: String,
        val tipCategory: String?,
        val isInSearchHistory: Boolean
    )
}