package com.example.motsi.feature.search.impl.data.networkservice

import com.example.motsi.feature.search.impl.models.data.SearchListDataModel
import com.example.motsi.feature.search.impl.models.data.SearchScreenDataModel
import com.example.motsi.feature.search.impl.models.data.SearchTipsDataModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface SearchRemoteDataSource {

    @GET("SearchScreen")
    suspend fun  getSearchScreen(): Response<SearchScreenDataModel>

    @GET("SearchList")
    suspend fun  getSearchList(): Response<SearchListDataModel>

    @POST("SearchTips")
    suspend fun  getSearchTips(@Body text: String): Response<SearchTipsDataModel>
}