package com.example.motsi.feature.search.impl.data.repository

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.data.ApiResponseHandler
import com.example.motsi.core.network.data.requestMapped
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.data.converter.SearchListConverter
import com.example.motsi.feature.search.impl.data.converter.SearchScreenConverter
import com.example.motsi.feature.search.impl.data.networkservice.SearchRemoteDataSource
import com.example.motsi.feature.search.impl.domain.repository.SearchRepository
import com.example.motsi.feature.search.impl.models.domain.SearchListDomainModel
import com.example.motsi.feature.search.impl.models.domain.SearchScreenDomainModel
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource,
    private val searchScreenConverter: SearchScreenConverter,
    private val searchListConverter: SearchListConverter,
    private val apiResponseHandler: ApiResponseHandler,
) : SearchRepository {

    override suspend fun getSearchScreen(): ResultWrapper<SearchScreenDomainModel, MotsiError> =
         apiResponseHandler.requestMapped(
            call = { remoteDataSource.getSearchScreen() },
            mapper = searchScreenConverter::toDomain
        )



    override suspend fun getSearchList(): ResultWrapper<SearchListDomainModel, MotsiError> =
        apiResponseHandler.requestMapped(
            call = { remoteDataSource.getSearchList() },
            mapper = searchListConverter::toDomain
        )
}
