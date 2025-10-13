package com.example.motsi.feature.search.impl.models.domain

import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable

internal data class SearchTipsListModel(
    val tipList: ImmutableList<SearchTip>
)

@Serializable
data class SearchTip(
    val type: String,
    val value: String,
    val tipTitle: String,
    val categoryTitle: String?,
    val icon: String
)