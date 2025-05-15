package com.example.motsi.feature.search.impl.models.domain

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class SearchListActivityModel(
    val recommendationList: ImmutableList<Activity> = persistentListOf()
){
    data class Activity(val id: String)
}