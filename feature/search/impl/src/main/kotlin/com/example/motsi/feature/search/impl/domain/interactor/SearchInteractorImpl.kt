package com.example.motsi.feature.search.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.domain.repository.SearchRepository
import com.example.motsi.feature.search.impl.models.domain.SearchListActivityModel
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel.AppBar
import com.example.motsi.feature.search.impl.models.domain.SearchTipsListModel
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

internal class SearchInteractorImpl @Inject constructor(private val repository: SearchRepository) :
    SearchInteractor {

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


//    override suspend fun getSearchTips(text: String): ResultWrapper<SearchTipsListModel, MotsiError> =
//        repository.getSearchTips(text)


    override suspend fun getSearchTips(text: String): ResultWrapper<SearchTipsListModel, MotsiError> {
        return ResultWrapper.Success(
            SearchTipsListModel(
                tipsList = persistentListOf(
                    SearchTipsListModel.Item(
                        tip = "собака",
                        tipCategory = "животные",
                        isInSearchHistory = true
                    ),
                    SearchTipsListModel.Item(
                        tip = "программирование на kotlin",
                        tipCategory = "IT",
                        isInSearchHistory = true
                    ),
                    SearchTipsListModel.Item(
                        tip = "рецепты пиццы",
                        tipCategory = "кулинария",
                        isInSearchHistory = false
                    ),
                    SearchTipsListModel.Item(
                        tip = "путешествия по европе",
                        tipCategory = null,
                        isInSearchHistory = false
                    ),
                    SearchTipsListModel.Item(
                        tip = "собака",
                        tipCategory = "животные",
                        isInSearchHistory = true
                    ),
                    SearchTipsListModel.Item(
                        tip = "программирование на kotlin",
                        tipCategory = "IT",
                        isInSearchHistory = true
                    ),
                    SearchTipsListModel.Item(
                        tip = "рецепты пиццы",
                        tipCategory = "кулинария",
                        isInSearchHistory = false
                    ),
                    SearchTipsListModel.Item(
                        tip = "путешествия по европе",
                        tipCategory = null,
                        isInSearchHistory = false
                    ),
                    SearchTipsListModel.Item(
                        tip = "собака",
                        tipCategory = "животные",
                        isInSearchHistory = true
                    ),
                    SearchTipsListModel.Item(
                        tip = "программирование на kotlin",
                        tipCategory = "IT",
                        isInSearchHistory = true
                    ),
                    SearchTipsListModel.Item(
                        tip = "рецепты пиццы",
                        tipCategory = "кулинария",
                        isInSearchHistory = false
                    ),
                    SearchTipsListModel.Item(
                        tip = "путешествия по европе",
                        tipCategory = null,
                        isInSearchHistory = false
                    )
                )
            )
        )
    }
}