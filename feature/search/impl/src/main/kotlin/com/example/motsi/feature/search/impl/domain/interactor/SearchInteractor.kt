package com.example.motsi.feature.search.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel
import com.example.motsi.feature.search.impl.models.domain.SearchSportActivityListModel
import com.example.motsi.feature.search.impl.models.domain.SearchTipsListModel
import com.example.motsi.feature.search.impl.models.presentation.SearchDestination

internal interface SearchInteractor {

    suspend fun getSearchScreen(): ResultWrapper<SearchScreenModel, MotsiError>

    suspend fun getSportActivityList(filterData: SearchDestination.SearchFilterData = SearchDestination.SearchFilterData()): ResultWrapper<SearchSportActivityListModel, MotsiError>

    suspend fun getTipList(text: String): ResultWrapper<SearchTipsListModel, MotsiError>
}