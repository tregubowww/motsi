package com.example.motsi.feature.search.impl.models.data

import kotlinx.serialization.SerialName

data class SearchTipsDataModel(
    @SerialName("tipsList")
    val tipsList: List<Item>
) {
    data class Item(
        @SerialName("tip")
        val tip: String,

        @SerialName("tipCategory")
        val tipCategory: String,

        @SerialName("isInSearchHistory")
        val isInSearchHistory: Boolean
    )
}