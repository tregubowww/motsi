package com.example.motsi.feature.search.impl.data.converter

import com.example.motsi.feature.search.impl.models.data.SearchListDataModel
import com.example.motsi.feature.search.impl.models.domain.SearchListDomainModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList


internal class SearchListConverter @Inject constructor() {

    fun toDomain(searchScreen: SearchListDataModel): SearchListDomainModel =
        with(searchScreen) {
            SearchListDomainModel(
                recommendationList = recommendationList.map {
                    SearchListDomainModel.Activity(
                        id = it.id
                    )
                }.toImmutableList()
            )
        }
}