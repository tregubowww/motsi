package com.example.motsi.feature.search.impl.domain.repository

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.models.domain.SearchListDomainModel
import com.example.motsi.feature.search.impl.models.domain.SearchScreenDomainModel

internal interface SearchRepository {

    suspend fun  getSearchScreen(): ResultWrapper<SearchScreenDomainModel, MotsiError>

    suspend fun  getSearchList(): ResultWrapper<SearchListDomainModel, MotsiError>
}