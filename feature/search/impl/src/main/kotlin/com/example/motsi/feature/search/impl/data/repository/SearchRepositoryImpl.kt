package com.example.motsi.feature.search.impl.data.repository

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.data.ApiResponseHandler
import com.example.motsi.core.network.data.requestMapped
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.data.converter.SearchListConverter
import com.example.motsi.feature.search.impl.data.converter.SearchScreenConverter
import com.example.motsi.feature.search.impl.data.networkservice.SearchRemoteDataSource
import com.example.motsi.feature.search.impl.domain.repository.SearchRepository
import com.example.motsi.feature.search.impl.models.domain.SearchListActivityModel
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource,
    private val searchScreenConverter: SearchScreenConverter,
    private val searchListConverter: SearchListConverter,
    private val apiResponseHandler: ApiResponseHandler,
) : SearchRepository {

    private val _searchQuery = MutableStateFlow("")
    override val searchQuery: StateFlow<String> = _searchQuery

    override suspend fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }


    override suspend fun getSearchScreen(): ResultWrapper<SearchScreenModel, MotsiError> =
         apiResponseHandler.requestMapped(
            call = { remoteDataSource.getSearchScreen() },
            mapper = searchScreenConverter::toDomain
        )



    override suspend fun getSearchList(): ResultWrapper<SearchListActivityModel, MotsiError> =
        apiResponseHandler.requestMapped(
            call = { remoteDataSource.getSearchList() },
            mapper = searchListConverter::toDomain
        )
}