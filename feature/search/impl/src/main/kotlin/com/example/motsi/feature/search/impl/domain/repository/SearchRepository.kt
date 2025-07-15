package com.example.motsi.feature.search.impl.domain.repository

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.models.domain.SearchListActivityModel
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel
import kotlinx.coroutines.flow.StateFlow

internal interface SearchRepository {

    suspend fun  getSearchScreen(): ResultWrapper<SearchScreenModel, MotsiError>

    suspend fun  getSearchList(): ResultWrapper<SearchListActivityModel, MotsiError>

    val searchQuery: StateFlow<String>
    suspend fun updateSearchQuery(query: String)
}