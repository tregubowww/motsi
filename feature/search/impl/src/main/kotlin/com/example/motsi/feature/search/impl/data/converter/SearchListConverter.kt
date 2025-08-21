package com.example.motsi.feature.search.impl.data.converter

import com.example.motsi.feature.search.impl.models.data.SearchListDataModel
import com.example.motsi.feature.search.impl.models.domain.SearchListActivityModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList


internal class SearchListConverter @Inject constructor() {

//    fun toDomain(searchScreen: SearchListDataModel): SearchListActivityModel =
//        with(searchScreen) {
//            SearchListActivityModel(
//                recommendationList = recommendationList.map {
//                    SearchListActivityModel.Activity(
//                        id = it.id
//                    )
//                }.toImmutableList()
//            )
//        }
}