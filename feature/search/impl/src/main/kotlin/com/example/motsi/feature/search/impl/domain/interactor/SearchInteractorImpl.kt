package com.example.motsi.feature.search.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.domain.repository.SearchRepository
import com.example.motsi.feature.search.impl.models.domain.SearchListSportActivityModel
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel
import com.example.motsi.feature.search.impl.models.domain.SearchSportActivityTip
import com.example.motsi.feature.search.impl.models.domain.SearchTipsListModel
import com.example.motsi.feature.search.impl.models.presentation.SearchDestination
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

internal class SearchInteractorImpl @Inject constructor(private val repository: SearchRepository) :
    SearchInteractor {

    override suspend fun getSearchScreen(): ResultWrapper<SearchScreenModel, MotsiError> =
        ResultWrapper.Success(
            SearchScreenModel(
                    defaultSearchHint = "Поиск",
            )
        )


    override suspend fun getSportActivityList(filterData: SearchDestination.SearchFilterData): ResultWrapper<SearchListSportActivityModel, MotsiError>{
//        = repository.getSearchList()
        return  ResultWrapper.Success(
            SearchListSportActivityModel(
                searchQuery = null,
                searchHint = "Поиск в Москве",
                recommendationActivityList = persistentListOf(),
                historyTipList = persistentListOf(
                    SearchSportActivityTip(
                        type = "вид спорта",
                        value = "футбол",
                        tipTitle = "игра мини футбол",
                        сategoryTitle = "футбол",
                        icon = "ic_clock_history_20"
                    ),
                    SearchSportActivityTip(
                        type = "вид спорта",
                        value = "баскетбол",
                        tipTitle = "игра стритбол",
                        сategoryTitle = "баскетбол",
                        icon = "ic_clock_history_20"
                    )
                )
            )
        )
    }

    override suspend fun getTipList(text: String): ResultWrapper<SearchTipsListModel, MotsiError> {
        return ResultWrapper.Success(
            SearchTipsListModel(
                tipList = persistentListOf(
                    SearchSportActivityTip(
                        type = "вид спорта",
                        value = "футбол",
                        tipTitle = "игра мини футбол",
                        сategoryTitle = "футбол",
                        icon = "ic_search_20"
                    ),
                    SearchSportActivityTip(
                        type = "вид спорта",
                        value = "баскетбол",
                        tipTitle = "игра стритбол",
                        сategoryTitle = "баскетбол",
                        icon = "ic_search_20"
                    ),
                    SearchSportActivityTip(
                        type = "вид спорта",
                        value = "теннис",
                        tipTitle = "теннис",
                        сategoryTitle = "теннис",
                        icon = "ic_search_20"
                    ),
                )
            )
        )
    }
}