package com.example.motsi.feature.search.impl.data.networkservice

import com.example.motsi.feature.search.impl.models.data.SearchListDataModel
import com.example.motsi.feature.search.impl.models.data.SearchScreenDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface SearchRemoteDataSource {

    @GET("SearchScreen")
    suspend fun  getSearchScreen(): Response<SearchScreenDataModel>

    @GET("SearchList")
    suspend fun  getSearchList(): Response<SearchListDataModel>
}