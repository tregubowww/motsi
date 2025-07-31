package com.example.motsi.feature.search.impl.models.presentation

import kotlinx.serialization.Serializable

@Serializable
internal data class SearchDestination(
    val searchQuery: String?
)

@Serializable
internal data class SearchTipsDestination(
    val searchQuery: String?,
    val appBarHint: String
)