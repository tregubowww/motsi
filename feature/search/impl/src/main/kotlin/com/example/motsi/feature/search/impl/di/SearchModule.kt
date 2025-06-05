package com.example.motsi.feature.search.impl.di

import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.network.data.ApiResponseHandler
import com.example.motsi.feature.search.impl.data.converter.SearchListConverter
import com.example.motsi.feature.search.impl.data.converter.SearchScreenConverter
import com.example.motsi.feature.search.impl.data.networkservice.SearchRemoteDataSource
import com.example.motsi.feature.search.impl.data.repository.SearchRepositoryImpl
import com.example.motsi.feature.search.impl.domain.interactor.SearchInteractor
import com.example.motsi.feature.search.impl.domain.interactor.SearchInteractorImpl
import com.example.motsi.feature.search.impl.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
internal class SearchModule {

    @FeatureScope
    @Provides
    fun provideUserApi(
        retrofit: Retrofit
    ): SearchRemoteDataSource =
        retrofit.create(SearchRemoteDataSource::class.java)

    @FeatureScope
    @Provides
    fun provideSearchInteractor(searchRepository: SearchRepository): SearchInteractor =
        SearchInteractorImpl(searchRepository)

    @FeatureScope
    @Provides
    fun provideSearchRepository(
        networkService: SearchRemoteDataSource,
        searchScreenConverter: SearchScreenConverter,
        searchListConverter: SearchListConverter,
        apiResponseHandler: ApiResponseHandler,
    ): SearchRepository =
        SearchRepositoryImpl(
            networkService,
            searchScreenConverter,
            searchListConverter,
            apiResponseHandler
        )
}