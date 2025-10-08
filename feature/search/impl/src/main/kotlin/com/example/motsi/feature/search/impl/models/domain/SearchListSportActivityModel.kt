package com.example.motsi.feature.search.impl.models.domain

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class SearchListSportActivityModel(
    val searchQuery: String?,
    val searchHint: String?,
    val recommendationActivityList: ImmutableList<Activity> = persistentListOf(),
    val historyTipList: ImmutableList<SearchSportActivityTip> = persistentListOf(),
){
    data class Activity(val id: String)
}