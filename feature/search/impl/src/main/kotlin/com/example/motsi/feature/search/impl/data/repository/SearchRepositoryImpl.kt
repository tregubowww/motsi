package com.example.motsi.feature.search.impl.data.repository

import com.example.motsi.core.network.data.ApiResponseHandler
import com.example.motsi.core.wrappers.infrastructure.MapboxWrapper
import com.example.motsi.feature.search.impl.data.converter.SearchScreenConverter
import com.example.motsi.feature.search.impl.data.networkservice.SearchRemoteDataSource
import com.example.motsi.feature.search.impl.domain.repository.SearchRepository
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource,
    private val searchScreenConverter: SearchScreenConverter,
//    private val searchListConverter: SearchListConverter,
    private val apiResponseHandler: ApiResponseHandler,
    ) : SearchRepository {

//    override suspend fun getSearchScreen(): ResultWrapper<SearchScreenModel, MotsiError> =
//         apiResponseHandler.requestMapped(
//            call = { remoteDataSource.getSearchScreen() },
//            mapper = searchScreenConverter::toDomain
//        )
//
//
//
//    override suspend fun getSearchList(): ResultWrapper<SearchListActivityModel, MotsiError> =
//        apiResponseHandler.requestMapped(
//            call = { remoteDataSource.getSearchList() },
//            mapper = searchListConverter::toDomain
//        )
}
