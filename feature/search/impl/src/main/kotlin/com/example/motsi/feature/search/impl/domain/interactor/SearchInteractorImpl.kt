package com.example.motsi.feature.search.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel
import com.example.motsi.feature.search.impl.models.domain.SearchSportActivityListModel
import com.example.motsi.feature.search.impl.models.domain.SearchTip
import com.example.motsi.feature.search.impl.models.domain.SearchTipsListModel
import com.example.motsi.feature.search.impl.models.presentation.SearchDestination
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

internal class SearchInteractorImpl @Inject constructor(
//    private val repository: SearchRepository
) : SearchInteractor {

    override suspend fun getSearchScreen(): ResultWrapper<SearchScreenModel, MotsiError> =
        ResultWrapper.Success(
            SearchScreenModel(
                defaultSearchHint = "Поиск",
                buttonTextForListOpen = "Показать список",//Не нужно
                dataSnackbarText = SearchScreenModel.DataSnackbarText(
                    dataSnackbarPermission = SearchScreenModel.DataSnackbarText.DataSnackbar(
                        message = "Необходимо разрешение на доступ к местоположению"
                    ),
                    dataSnackbarInternet = SearchScreenModel.DataSnackbarText.DataSnackbar(
                        message = "Нет подключения к интернету. Попробуйте позже"
                    ),
                    dataSnackbarLocation = SearchScreenModel.DataSnackbarText.DataSnackbar(
                        message = "Источники определания координат недоступны. Пожалуйста, проверьте настройки",
                        actionLabel = "Настройки"
                    ),
                    dataSnackbarLoadingLocation = SearchScreenModel.DataSnackbarText.DataSnackbar(
                        message = "Дождитесь определения геопозиции"
                    ),
                ),
            )
        )


    override suspend fun getSportActivityList(filterData: SearchDestination.SearchFilterData): ResultWrapper<SearchSportActivityListModel, MotsiError>{
//        = repository.getSearchList()
        return ResultWrapper.Success(
            SearchSportActivityListModel(
                searchQuery = null,
                searchHint = "Поиск в Москве",
                cityLocation = SearchSportActivityListModel.SportActivity.CityLocation(
                    Pair(44.791101, 38.621272),
                    14.0
                ),

                sportActivityList = persistentListOf(
                    SearchSportActivityListModel.SportActivity(
                        id = "1",
                        participantList = persistentListOf(
                            SearchSportActivityListModel.SportActivity.Participant(
                                urlUserPic = "https://avatars.mds.yandex.net/i?id=9b20bc9c3d7884532228551baf8c5bd2_l-5219738-images-thumbs&n=13",
                            ),
                            SearchSportActivityListModel.SportActivity.Participant(
                                urlUserPic = "https://avatars.mds.yandex.net/i?id=bf939e1f52344460d5b62b4f75bc8e91_l-5652396-images-thumbs&n=13",
                            ),
                            SearchSportActivityListModel.SportActivity.Participant(
                                urlUserPic = "https://avatars.mds.yandex.net/i?id=06fa91770623ade335e398fbf64916f1_l-12757031-images-thumbs&n=13",
                            ),
                            SearchSportActivityListModel.SportActivity.Participant(
                                urlUserPic = "https://avatars.mds.yandex.net/i?id=6f9ec677ac008b86814061aa2eff7daf_l-16457393-images-thumbs&n=13"
                            ),
                            SearchSportActivityListModel.SportActivity.Participant(
                                "https://avatars.mds.yandex.net/i?id=402ace28b60f991145720a96a00d0a9b_l-5434761-images-thumbs&n=13"
                            ),

                            SearchSportActivityListModel.SportActivity.Participant(
                                "https://bookmaker-ratings.ru/wp-content/uploads/2017/07/1498632318_theboodlestenniseventw0nh96ark6fx.jpg"
                            ),
                            SearchSportActivityListModel.SportActivity.Participant(
                                "https://static.independent.co.uk/s3fs-public/thumbnails/image/2017/05/15/20/federer-2009.jpg",
                            ),
                            SearchSportActivityListModel.SportActivity.Participant(
                                "https://i.pinimg.com/736x/d3/22/70/d3227097fde0b001068f293c44c2d6fd.jpg",
                            ),
                            SearchSportActivityListModel.SportActivity.Participant(
                                "https://mir-s3-cdn-cf.behance.net/project_modules/1400/f47e4851919173.58fe9dc814575.jpg",
                            ),
                            SearchSportActivityListModel.SportActivity.Participant(
                                "https://avatars.mds.yandex.net/i?id=34fd61f1d4e2835c3000009ea5c52c64_l-12501487-images-thumbs&n=13",
                            ),
                            SearchSportActivityListModel.SportActivity.Participant(
                                "https://avatars.mds.yandex.net/i?id=073e2c1fbf57e194dea705012bc8d930_l-4987768-images-thumbs&n=13",
                            ),
                            SearchSportActivityListModel.SportActivity.Participant(
                                "https://bookmaker-ratings.ru/wp-content/uploads/2017/07/1498632318_theboodlestenniseventw0nh96ark6fx.jpg",
                            )
                        ),
                        typeSport = "Бег",
                        descriptionActivity = "Интервальная тренировка",
                        dateText = "18 августа 14:00",
                        locationText = "Крымская ул. 22",
                        iconTypeSport = "ic_sport_type_run_24",
                        colorTypeSport = "type_sport_color_run",
                        privateStatus = SearchSportActivityListModel.SportActivity.PrivateStatus.PRIVATE,
                        mapData = SearchSportActivityListModel.SportActivity.MapData(
                            locationPoint = Pair(44.785820, 38.681040),
                            zoom = 14f,
                            iconMark = "ic_clock_history_20"
                        )
                    ),
                    SearchSportActivityListModel.SportActivity(
                        id = "2",
                        participantList = persistentListOf(
                            SearchSportActivityListModel.SportActivity.Participant(
                                urlUserPic = "https://avatars.mds.yandex.net/i?id=9b20bc9c3d7884532228551baf8c5bd2_l-5219738-images-thumbs&n=13",
                            ),
                            SearchSportActivityListModel.SportActivity.Participant(
                                urlUserPic = "https://avatars.mds.yandex.net/i?id=bf939e1f52344460d5b62b4f75bc8e91_l-5652396-images-thumbs&n=13",
                            ),
                            SearchSportActivityListModel.SportActivity.Participant(
                                urlUserPic = "https://avatars.mds.yandex.net/i?id=06fa91770623ade335e398fbf64916f1_l-12757031-images-thumbs&n=13",
                            ),
                            SearchSportActivityListModel.SportActivity.Participant(
                                urlUserPic = "https://avatars.mds.yandex.net/i?id=6f9ec677ac008b86814061aa2eff7daf_l-16457393-images-thumbs&n=13"
                            ),
                            SearchSportActivityListModel.SportActivity.Participant(
                                "https://avatars.mds.yandex.net/i?id=402ace28b60f991145720a96a00d0a9b_l-5434761-images-thumbs&n=13"
                            )
                        ),
                        iconTypeSport = "ic_sport_type_run_24",
                        typeSport = "Бег",
                        descriptionActivity = "Интервальная тренировка",
                        dateText = "18 августа 14:00",
                        locationText = "Крымская ул. 22",
                        colorTypeSport = "type_sport_color_run",
                        privateStatus = SearchSportActivityListModel.SportActivity.PrivateStatus.OPEN,
                        mapData = SearchSportActivityListModel.SportActivity.MapData(
                            locationPoint = Pair(44.792234, 38.650809),
                            zoom = 14f,
                            iconMark = "ic_filter_24"
                        )
                    ),
                    SearchSportActivityListModel.SportActivity(
                        id = "3",
                        participantList = persistentListOf(
                            SearchSportActivityListModel.SportActivity.Participant(
                                urlUserPic = "https://avatars.mds.yandex.net/i?id=9b20bc9c3d7884532228551baf8c5bd2_l-5219738-images-thumbs&n=13",
                            ),
                            SearchSportActivityListModel.SportActivity.Participant(
                                urlUserPic = "https://avatars.mds.yandex.net/i?id=bf939e1f52344460d5b62b4f75bc8e91_l-5652396-images-thumbs&n=13",
                            ),
                            SearchSportActivityListModel.SportActivity.Participant(
                                urlUserPic = "https://avatars.mds.yandex.net/i?id=06fa91770623ade335e398fbf64916f1_l-12757031-images-thumbs&n=13",
                            ),
                            SearchSportActivityListModel.SportActivity.Participant(
                                urlUserPic = "https://avatars.mds.yandex.net/i?id=6f9ec677ac008b86814061aa2eff7daf_l-16457393-images-thumbs&n=13"
                            ),
                            SearchSportActivityListModel.SportActivity.Participant(
                                "https://avatars.mds.yandex.net/i?id=402ace28b60f991145720a96a00d0a9b_l-5434761-images-thumbs&n=13"
                            )
                        ),
                        iconTypeSport = "ic_sport_type_run_24",
                        typeSport = "Бег",
                        descriptionActivity = "Интервальная тренировка",
                        dateText = "18 августа 14:00",
                        locationText = "Крымская ул. 22",
                        colorTypeSport = "type_sport_color_run",
                        privateStatus = SearchSportActivityListModel.SportActivity.PrivateStatus.OPEN,
                        mapData = SearchSportActivityListModel.SportActivity.MapData(
                            locationPoint = Pair(44.748344, 38.673590),
                            zoom = 14f,
                            iconMark = "ic_search_20"
                        )
                    )
                ),
                historyTipList = persistentListOf(
                    SearchTip(
                        type = "вид спорта",
                        value = "футбол",
                        tipTitle = "игра мини футбол",
                        categoryTitle = "футбол",
                        icon = "ic_clock_history_20"
                    ),
                    SearchTip(
                        type = "вид спорта",
                        value = "баскетбол",
                        tipTitle = "игра стритбол",
                        categoryTitle = "баскетбол",
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
                    SearchTip(
                        type = "вид спорта",
                        value = "футбол",
                        tipTitle = "игра мини футбол",
                        categoryTitle = "футбол",
                        icon = "ic_search_20"
                    ),
                    SearchTip(
                        type = "вид спорта",
                        value = "баскетбол",
                        tipTitle = "игра стритбол",
                        categoryTitle = "баскетбол",
                        icon = "ic_search_20"
                    ),
                    SearchTip(
                        type = "вид спорта",
                        value = "теннис",
                        tipTitle = "теннис",
                        categoryTitle = "теннис",
                        icon = "ic_search_20"
                    ),
                )
            )
        )
    }
}