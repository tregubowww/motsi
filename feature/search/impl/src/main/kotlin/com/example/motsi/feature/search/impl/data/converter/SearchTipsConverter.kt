package com.example.motsi.feature.search.impl.data.converter

import com.example.motsi.feature.search.impl.models.data.SearchTipsDataModel
import com.example.motsi.feature.search.impl.models.domain.SearchTipsListModel
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

internal class SearchTipsConverter @Inject constructor() {

    fun toDomain(searchTips: SearchTipsDataModel): SearchTipsListModel =
        with(searchTips) {
            SearchTipsListModel(
                tipsList = tipsList.map { item ->
                    SearchTipsListModel.Item(
                        tip = item.tip,
                        tipCategory = item.tipCategory,
                        isInSearchHistory = item.isInSearchHistory
                    )
                }.toImmutableList()
            )
        }
}