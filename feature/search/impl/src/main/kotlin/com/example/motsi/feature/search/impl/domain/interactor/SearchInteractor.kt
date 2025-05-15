package com.example.motsi.feature.search.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.models.domain.SearchListDomainModel
import com.example.motsi.feature.search.impl.models.domain.SearchScreenDomainModel

internal interface SearchInteractor {

    suspend fun  getSearchScreen(): ResultWrapper<SearchScreenDomainModel, MotsiError>

    suspend fun  getSearchList(): ResultWrapper<SearchListDomainModel, MotsiError>
}