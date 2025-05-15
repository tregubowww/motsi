package com.example.motsi.feature.search.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.domain.repository.SearchRepository
import com.example.motsi.feature.search.impl.models.domain.SearchListDomainModel
import com.example.motsi.feature.search.impl.models.domain.SearchScreenDomainModel
import javax.inject.Inject

internal class SearchInteractorImpl @Inject constructor(private val repository: SearchRepository) :
    SearchInteractor {

    override suspend fun getSearchScreen(): ResultWrapper<SearchScreenDomainModel, MotsiError> =
        repository.getSearchScreen()

    override suspend fun getSearchList(): ResultWrapper<SearchListDomainModel, MotsiError> =
        repository.getSearchList()
}