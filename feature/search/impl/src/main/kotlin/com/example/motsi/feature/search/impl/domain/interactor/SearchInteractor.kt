package com.example.motsi.feature.search.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.models.domain.SearchListActivityModel
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel
import com.example.motsi.feature.search.impl.models.domain.SearchTipsListModel

internal interface SearchInteractor {

    suspend fun getSearchScreen(): ResultWrapper<SearchScreenModel, MotsiError>

    suspend fun getSearchList(): ResultWrapper<SearchListActivityModel, MotsiError>

    suspend fun getSearchTips(text: String): ResultWrapper<SearchTipsListModel, MotsiError>
}