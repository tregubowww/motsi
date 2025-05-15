package com.example.motsi.feature.search.impl.models.domain

import kotlinx.collections.immutable.ImmutableList

internal data class SearchListDomainModel(
    val recommendationList: ImmutableList<Activity>
){
    data class Activity(val id: String)
}