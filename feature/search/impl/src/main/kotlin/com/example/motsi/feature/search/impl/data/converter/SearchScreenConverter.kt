package com.example.motsi.feature.search.impl.data.converter

import com.example.motsi.feature.search.impl.models.data.SearchScreenDataModel
import com.example.motsi.feature.search.impl.models.domain.SearchScreenDomainModel
import javax.inject.Inject

internal class SearchScreenConverter @Inject constructor() {

    fun toDomain(searchScreen: SearchScreenDataModel): SearchScreenDomainModel =
        with(searchScreen){
            SearchScreenDomainModel(
                appbar = SearchScreenDomainModel.AppBar(
                    titleSearchField = appBarTitle,
                    navigationAction = null,
                    actions = emptySet()

                )
            )
        }
}