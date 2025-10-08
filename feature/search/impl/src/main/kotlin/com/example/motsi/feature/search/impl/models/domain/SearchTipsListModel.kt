package com.example.motsi.feature.search.impl.models.domain

import kotlinx.collections.immutable.ImmutableList

internal data class SearchTipsListModel(
    val tipList: ImmutableList<SearchSportActivityTip>
)