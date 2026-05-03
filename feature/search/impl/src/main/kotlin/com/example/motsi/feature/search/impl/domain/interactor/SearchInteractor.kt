package com.example.motsi.feature.search.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.models.domain.SearchFilterModel
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel
import com.example.motsi.feature.search.impl.models.domain.SearchSportActivityListModel
import com.example.motsi.feature.search.impl.models.domain.SearchTipsListModel

internal interface SearchInteractor {

    suspend fun getSearchScreen(): ResultWrapper<SearchScreenModel, MotsiError>

    suspend fun getSportActivityList(
        filterData: SearchFilterModel = SearchFilterModel()
    ): ResultWrapper<SearchSportActivityListModel, MotsiError>

    suspend fun getTipList(text: String): ResultWrapper<SearchTipsListModel, MotsiError>
}