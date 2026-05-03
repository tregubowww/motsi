package com.example.motsi.core.wizard.impl.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel
import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel.Action
import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel.AppBar
import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel.MapScreenModel.Points.Point
import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel.MapScreenModel.Points.Point.Participant
import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel.MapScreenModel.Points.Tip
import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel.Screen
import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel.Widget
import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel.Widget.Item
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

internal class WizardInteractorImpl @Inject constructor() :
    WizardInteractor {

    override suspend fun getNextScreen(
        properties: Map<String, String>,
        step: Int
    ): ResultWrapper<WizardCoordinatorModel, MotsiError> {
        return when {
            step == 1 -> {
                ResultWrapper.Success(
                    WizardCoordinatorModel(
                        screen = Screen.BaseScreen(
                            WizardCoordinatorModel.BaseScreenModel(
                                currentStep = 1,
                                appBar = AppBar(
                                    title = "Вид спорта",
                                    iconNavigation = "ic_cross_24",
                                    actions = mapOf(
                                        "NAVIGATION_ACTION_KEY" to Action.ExitWizardFlow(
                                            properties = mapOf("currentStep" to "1")
                                        )
                                    ),
                                    properties = emptyMap()
                                ),
                                listWidget = persistentListOf(
                                    Widget(
                                        id = "1",
                                        type = "IconTextWidget",
                                        validators = null,
                                        properties = mapOf(
                                            "TITLE_KEY" to "Соревнования",
                                            "DIVIDER_FLAG_KEY" to "true"
                                        ),
                                        items = null,
                                        actions = mapOf(
                                            "ON_FIELD_CLICK_ACTION_KEY" to Action.NextScreen(
                                                properties = mapOf("currentStep" to "2")
                                            )
                                        )
                                    ),
                                    Widget(
                                        id = "2",
                                        type = "IconTextWidget",
                                        validators = null,
                                        properties = mapOf(
                                            "TITLE_KEY" to "Тренировка",
                                            "DIVIDER_FLAG_KEY" to "false"
                                        ),
                                        items = null,
                                        actions = mapOf(
                                            "ON_FIELD_CLICK_ACTION_KEY" to Action.NextScreen(
                                                properties = mapOf("currentStep" to "2")
                                            )
                                        )
                                    ),

                                    ),
                                bottomBar = null
                            )
                        )
                    )
                )
            }

            step == 2 -> {
                ResultWrapper.Success(
                    WizardCoordinatorModel(
                        screen = Screen.BaseScreen(
                            WizardCoordinatorModel.BaseScreenModel(
                                currentStep = 2,
                                appBar = AppBar(
                                    title = "Вид спорта",
                                    iconNavigation = "ic_back_24",
                                    actions = mapOf(
                                        "NAVIGATION_ACTION_KEY" to Action.PreviewScreen
                                    ),
                                    properties = emptyMap()
                                ),
                                listWidget = persistentListOf(
                                    Widget(
                                        id = "2",
                                        type = "SearchWizardWidget",
                                        validators = null,
                                        actions = null,
                                        properties = null,
                                        items = persistentListOf(
                                            Item(
                                                id = "1",
                                                title = "Бег по шоссе",
                                                icon = "ic_filter_24",
                                                actions = mapOf(
                                                    "ON_FIELD_CLICK_ACTION_KEY" to Action.NextScreen(
                                                        properties = mapOf("id" to "1")
                                                    )
                                                ),
                                                subtitle = null,
                                                description = null,
                                                validators = null,
                                                properties = emptyMap()
                                            ),
                                            Item(
                                                id = "2",
                                                title = "Бег по стадиону",
                                                icon = null,
                                                actions = mapOf(
                                                    "ON_FIELD_CLICK_ACTION_KEY" to Action.NextScreen(
                                                        properties = mapOf("id" to "1")
                                                    )
                                                ),
                                                subtitle = null,
                                                description = null,
                                                validators = null,
                                                properties = emptyMap()
                                            ),
                                            Item(
                                                id = "3",
                                                title = "Трэйлранинг",
                                                icon = null,
                                                actions = mapOf(
                                                    "ON_FIELD_CLICK_ACTION_KEY" to Action.NextScreen(
                                                        properties = mapOf("id" to "1")
                                                    )
                                                ),
                                                subtitle = null,
                                                description = null,
                                                validators = null,
                                                properties = emptyMap()
                                            )
                                        )
                                    )
                                ),
                                bottomBar = null
                            )
                        )
                    )
                )
            }

            step == 3 -> {
                ResultWrapper.Success(
                    WizardCoordinatorModel(
                        screen = Screen.MapScreen(
                            WizardCoordinatorModel.MapScreenModel(
                                idScreen = "3",
                                currentStep = 3,
                                appBar = AppBar(
                                    title = "Местопоположение",
                                    iconNavigation = "ic_back_24",
                                    actions = mapOf(
                                        "NAVIGATION_ACTION_KEY" to Action.PreviewScreen
                                    ),
                                    properties = mapOf("HINT_KEY" to "Поиск в Москве"),
                                ),
                                listWidget = persistentListOf(),
                                bottomBar = WizardCoordinatorModel.BottomBar(
                                    persistentListOf(
                                        Widget(
                                            id = "1",
                                            type = "ButtonWizardWidget",
                                            validators = null,
                                            properties = mapOf("TITLE_KEY" to "Выбрать"),
                                            items = null,
                                            actions = mapOf(
                                                "ON_FIELD_CLICK_ACTION_KEY" to Action.NextScreen(
                                                    properties = mapOf("currentStep" to "3")
                                                )
                                            )
                                        )
                                    )
                                ),
                                urlGetMarkers = "urlGetMarkers",
                                markers = WizardCoordinatorModel.MapScreenModel.Points(

                                    cityLocation = WizardCoordinatorModel.MapScreenModel.Points.Point.CityLocation(
                                        Pair(44.791101, 38.621272),
                                        14.0
                                    ),

                                    pointList = persistentListOf(
                                        Point(
                                            id = "1",
                                            participantList = persistentListOf(
                                                Participant(
                                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=9b20bc9c3d7884532228551baf8c5bd2_l-5219738-images-thumbs&n=13",
                                                ),
                                                Participant(
                                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=bf939e1f52344460d5b62b4f75bc8e91_l-5652396-images-thumbs&n=13",
                                                ),
                                                Participant(
                                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=06fa91770623ade335e398fbf64916f1_l-12757031-images-thumbs&n=13",
                                                ),
                                                Participant(
                                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=6f9ec677ac008b86814061aa2eff7daf_l-16457393-images-thumbs&n=13"
                                                ),
                                                Participant(
                                                    "https://avatars.mds.yandex.net/i?id=402ace28b60f991145720a96a00d0a9b_l-5434761-images-thumbs&n=13"
                                                ),

                                                Participant(
                                                    "https://bookmaker-ratings.ru/wp-content/uploads/2017/07/1498632318_theboodlestenniseventw0nh96ark6fx.jpg"
                                                ),
                                                Participant(
                                                    "https://static.independent.co.uk/s3fs-public/thumbnails/image/2017/05/15/20/federer-2009.jpg",
                                                ),
                                                Participant(
                                                    "https://i.pinimg.com/736x/d3/22/70/d3227097fde0b001068f293c44c2d6fd.jpg",
                                                ),
                                                Participant(
                                                    "https://mir-s3-cdn-cf.behance.net/project_modules/1400/f47e4851919173.58fe9dc814575.jpg",
                                                ),
                                                Participant(
                                                    "https://avatars.mds.yandex.net/i?id=34fd61f1d4e2835c3000009ea5c52c64_l-12501487-images-thumbs&n=13",
                                                ),
                                                Participant(
                                                    "https://avatars.mds.yandex.net/i?id=073e2c1fbf57e194dea705012bc8d930_l-4987768-images-thumbs&n=13",
                                                ),
                                                Participant(
                                                    "https://bookmaker-ratings.ru/wp-content/uploads/2017/07/1498632318_theboodlestenniseventw0nh96ark6fx.jpg",
                                                )
                                            ),
                                            typeSport = "Бег",
                                            descriptionActivity = "Интервальная тренировка",
                                            dateText = "18 августа 14:00",
                                            locationText = "Крымская ул. 22",
                                            iconTypeSport = "ic_sport_type_run_24",
                                            colorTypeSport = "type_sport_color_run",
                                            privateStatus = Point.PrivateStatus.PRIVATE,
                                            mapData = Point.MapData(
                                                locationPoint = Pair(44.785820, 38.681040),
                                                zoom = 14f,
                                                icon = "ic_sport_type_run_24",
                                                color = "color_sport_type_run",
                                                description = ""
                                            )
                                        ),
                                        Point(
                                            id = "2",
                                            participantList = persistentListOf(
                                                Participant(
                                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=9b20bc9c3d7884532228551baf8c5bd2_l-5219738-images-thumbs&n=13",
                                                ),
                                                Participant(
                                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=bf939e1f52344460d5b62b4f75bc8e91_l-5652396-images-thumbs&n=13",
                                                ),
                                                Participant(
                                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=06fa91770623ade335e398fbf64916f1_l-12757031-images-thumbs&n=13",
                                                ),
                                                Participant(
                                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=6f9ec677ac008b86814061aa2eff7daf_l-16457393-images-thumbs&n=13"
                                                ),
                                                Participant(
                                                    "https://avatars.mds.yandex.net/i?id=402ace28b60f991145720a96a00d0a9b_l-5434761-images-thumbs&n=13"
                                                )
                                            ),
                                            iconTypeSport = "ic_sport_type_run_24",
                                            typeSport = "Бег",
                                            descriptionActivity = "Интервальная тренировка",
                                            dateText = "18 августа 14:00",
                                            locationText = "Крымская ул. 22",
                                            colorTypeSport = "type_sport_color_run",
                                            privateStatus = Point.PrivateStatus.OPEN,
                                            mapData = Point.MapData(
                                                locationPoint = Pair(44.792234, 38.650809),
                                                zoom = 14f,
                                                icon = "ic_sport_type_run_24",
                                                color = "color_sport_type_run",
                                                description = ""
                                            )
                                        ),
                                        Point(
                                            id = "3",
                                            participantList = persistentListOf(
                                                Participant(
                                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=9b20bc9c3d7884532228551baf8c5bd2_l-5219738-images-thumbs&n=13",
                                                ),
                                                Participant(
                                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=bf939e1f52344460d5b62b4f75bc8e91_l-5652396-images-thumbs&n=13",
                                                ),
                                                Participant(
                                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=06fa91770623ade335e398fbf64916f1_l-12757031-images-thumbs&n=13",
                                                ),
                                                Participant(
                                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=6f9ec677ac008b86814061aa2eff7daf_l-16457393-images-thumbs&n=13"
                                                ),
                                                Participant(
                                                    "https://avatars.mds.yandex.net/i?id=402ace28b60f991145720a96a00d0a9b_l-5434761-images-thumbs&n=13"
                                                )
                                            ),
                                            iconTypeSport = "ic_sport_type_run_24",
                                            typeSport = "Бег",
                                            descriptionActivity = "Интервальная тренировка",
                                            dateText = "18 августа 14:00",
                                            locationText = "Крымская ул. 22",
                                            colorTypeSport = "type_sport_color_run",
                                            privateStatus = Point.PrivateStatus.OPEN,
                                            mapData = Point.MapData(
                                                locationPoint = Pair(44.748344, 38.673590),
                                                zoom = 14f,
                                                icon = "ic_sport_type_run_24",
                                                color = "color_sport_type_run",
                                                description = ""
                                            )
                                        )
                                    ),
                                    historyTipList = persistentListOf(
                                        Tip(
                                            type = "вид спорта",
                                            value = "футбол",
                                            tipTitle = "игра мини футбол",
                                            categoryTitle = "футбол",
                                            icon = "ic_clock_history_20"
                                        ),
                                        Tip(
                                            type = "вид спорта",
                                            value = "баскетбол",
                                            tipTitle = "игра стритбол",
                                            categoryTitle = "баскетбол",
                                            icon = "ic_clock_history_20"
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            }

            else -> {
                ResultWrapper.Success(
                    WizardCoordinatorModel(
                        screen = Screen.BaseScreen(
                            WizardCoordinatorModel.BaseScreenModel(
                                currentStep = 4,
                                appBar = AppBar(
                                    title = "Статус",
                                    iconNavigation = null,
                                    actions = mapOf(
                                        "NAVIGATION_ACTION_KEY" to Action.PreviewScreen
                                    ),
                                    properties = emptyMap()
                                ),
                                listWidget = persistentListOf(
                                    Widget(
                                        id = "1",
                                        type = "ButtonWizardWidget",
                                        validators = null,
                                        properties = mapOf("TITLE_KEY" to "Перейти в мои активности"),
                                        items = null,
                                        actions = mapOf(
                                            "ON_FIELD_CLICK_ACTION_KEY" to Action.ExitWizardFlow(
                                                properties = mapOf("currentStep" to "4")
                                            )
                                        )
                                    )
                                ),
                                bottomBar = null
                            )
                        )
                    )
                )
            }
        }
    }


    override suspend fun getItems(query: String, urlGetSearchItems: String) =
        ResultWrapper.Success(
            persistentListOf(
                Item(
                    id = "1",
                    title = "теннис большой",
                    actions = mapOf(
                        "ON_FIELD_CLICK_ACTION_KEY" to Action.NextScreen(
                            properties = mapOf("id" to "1")
                        )
                    ),
                    icon = null,
                    subtitle = null,
                    description = null,
                    validators = null,
                    properties = emptyMap()
                )
            )
        )

    override suspend fun getMarkersForMapScreen(urlGetMarkers: String) =
        ResultWrapper.Success(
            persistentListOf(
                Item(
                    id = "1",
                    title = "теннис большой",
                    actions = mapOf(
                        "ON_FIELD_CLICK_ACTION_KEY" to Action.NextScreen(
                            properties = mapOf("id" to "1")
                        )
                    ),
                    icon = null,
                    subtitle = null,
                    description = null,
                    validators = null,
                    properties = emptyMap()
                )
            )
        )
}