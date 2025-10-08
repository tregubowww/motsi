package com.example.motsi.feature.search.impl.models.domain

import kotlinx.serialization.Serializable


@Serializable
data class SearchSportActivityTip(
    val type: String,
    val value: String,
    val tipTitle: String,
    val сategoryTitle: String?,
    val icon: String
)