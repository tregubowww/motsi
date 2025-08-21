package com.example.motsi.feature.search.impl.models.presentation

import com.example.motsi.feature.search.impl.models.domain.SearchTip
import kotlinx.serialization.Serializable

@Serializable
internal data class SearchDestination(
    val filterData: SearchFilterData = SearchFilterData(),
) {

    @Serializable
     data class SearchFilterData(
        val type: String? = null,
        val value: String? = null,
    )
}


@Serializable
internal data class SearchTipsDestination(
    val entryData: EntryData
) {
    @Serializable
    data class EntryData(
        val searchQuery: String?,
        val searchHint: String,
        val historyTipList: List<SearchTip>,
    )
}
