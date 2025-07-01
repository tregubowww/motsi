package com.example.motsi.feature.search.impl.data.converter

import com.example.motsi.feature.search.impl.models.data.SearchScreenDataModel
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel
import kotlinx.collections.immutable.persistentSetOf
import javax.inject.Inject

internal class SearchScreenConverter @Inject constructor() {

    fun toDomain(searchScreen: SearchScreenDataModel): SearchScreenModel =
        with(searchScreen){
            SearchScreenModel(
                appbar = SearchScreenModel.AppBar(
                    titleSearchField = appBarTitle,
                    navigationAction = null,
                    actions = persistentSetOf()

                )
            )
        }
}