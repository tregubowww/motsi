package com.example.motsi.feature.search.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.domain.repository.SearchRepository
import com.example.motsi.feature.search.impl.models.domain.SearchListActivityModel
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel.AppBar
import com.example.motsi.feature.search.impl.models.domain.SearchTipsListModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

internal class SearchInteractorImpl @Inject constructor(private val repository: SearchRepository) :
    SearchInteractor {

    override val searchQuery: StateFlow<String> = repository.searchQuery


    override suspend fun updateSearchQuery(query: String) {
        repository.updateSearchQuery(query)
    }


    override suspend fun getSearchScreen(): ResultWrapper<SearchScreenModel, MotsiError> =
        ResultWrapper.Success(
            SearchScreenModel(
                appbar = AppBar(
                    titleSearchField = "Поиск в Азовской",
                )
            )
        )


    override suspend fun getSearchList(): ResultWrapper<SearchListActivityModel, MotsiError> =
        repository.getSearchList()


    override suspend fun getSearchTipsList(): ResultWrapper<SearchTipsListModel, MotsiError> {
        return ResultWrapper.Success(
            SearchTipsListModel(
                tipsList = persistentListOf(
                    SearchTipsListModel.SearchTipItem(
                        tips = "собака",
                        tipsCategory = "животные",
                        isInSearchHistory = true
                    ),
                    SearchTipsListModel.SearchTipItem(
                        tips = "программирование на kotlin",
                        tipsCategory = "IT",
                        isInSearchHistory = true
                    ),
                    SearchTipsListModel.SearchTipItem(
                        tips = "рецепты пиццы",
                        tipsCategory = "кулинария",
                        isInSearchHistory = false
                    ),
                    SearchTipsListModel.SearchTipItem(
                        tips = "путешествия по европе",
                        tipsCategory = null,
                        isInSearchHistory = false
                    ),
                    SearchTipsListModel.SearchTipItem(
                        tips = "собака",
                        tipsCategory = "животные",
                        isInSearchHistory = true
                    ),
                    SearchTipsListModel.SearchTipItem(
                        tips = "программирование на kotlin",
                        tipsCategory = "IT",
                        isInSearchHistory = true
                    ),
                    SearchTipsListModel.SearchTipItem(
                        tips = "рецепты пиццы",
                        tipsCategory = "кулинария",
                        isInSearchHistory = false
                    ),
                    SearchTipsListModel.SearchTipItem(
                        tips = "путешествия по европе",
                        tipsCategory = null,
                        isInSearchHistory = false
                    ),
                    SearchTipsListModel.SearchTipItem(
                        tips = "собака",
                        tipsCategory = "животные",
                        isInSearchHistory = true
                    ),
                    SearchTipsListModel.SearchTipItem(
                        tips = "программирование на kotlin",
                        tipsCategory = "IT",
                        isInSearchHistory = true
                    ),
                    SearchTipsListModel.SearchTipItem(
                        tips = "рецепты пиццы",
                        tipsCategory = "кулинария",
                        isInSearchHistory = false
                    ),
                    SearchTipsListModel.SearchTipItem(
                        tips = "путешествия по европе",
                        tipsCategory = null,
                        isInSearchHistory = false
                    )
                )
            )
        )
    }
}