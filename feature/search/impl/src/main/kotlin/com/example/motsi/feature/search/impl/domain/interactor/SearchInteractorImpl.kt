package com.example.motsi.feature.search.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.domain.repository.SearchRepository
import com.example.motsi.feature.search.impl.models.domain.SearchListActivityModel
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel.AppBar
import javax.inject.Inject

internal class SearchInteractorImpl @Inject constructor(private val repository: SearchRepository) :
    SearchInteractor {

    override suspend fun getSearchScreen(): ResultWrapper<SearchScreenModel, MotsiError> =
        ResultWrapper.Success(
            SearchScreenModel(
                appbar = AppBar(
                    titleSearchField = "Поиск в Азовской",
                )
            )
        )

    override suspend fun getSearchList(): ResultWrapper<SearchListActivityModel, MotsiError> =
        repository.getSearchList()
}