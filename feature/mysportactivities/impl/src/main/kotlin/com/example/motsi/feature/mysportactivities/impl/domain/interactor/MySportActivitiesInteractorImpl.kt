package com.example.motsi.feature.mysportactivities.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.mysportactivities.impl.models.domain.MySportActivitiesModel
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

internal class MySportActivitiesInteractorImpl @Inject constructor() :
    MySportActivitiesInteractor {
    override suspend fun getDataScreen(): ResultWrapper<MySportActivitiesModel, MotsiError> =
        ResultWrapper.Success(
            MySportActivitiesModel(
                appBar = MySportActivitiesModel.AppBar(title = "Мои активности"),
                addSportActivityButton = MySportActivitiesModel.AddSportActivityButton(
                    bottomBarButtonTitle = "Добавить активность",
                    urlStartWizardAddSportActivity = "urlStartWizardAddSportActivity"
                ),
                pageList = persistentListOf(
                    MySportActivitiesModel.Page(
                        title = "Участие",
                        items = persistentListOf(
                                MySportActivitiesModel.Page.Item(
                                    id = "1",
                                    participantList = persistentListOf(
                                        MySportActivitiesModel.Page.Item.Participant(
                                            urlUserPic = "https://avatars.mds.yandex.net/i?id=9b20bc9c3d7884532228551baf8c5bd2_l-5219738-images-thumbs&n=13",
                                        ),
                                        MySportActivitiesModel.Page.Item.Participant(
                                            urlUserPic = "https://avatars.mds.yandex.net/i?id=bf939e1f52344460d5b62b4f75bc8e91_l-5652396-images-thumbs&n=13",
                                        ),
                                        MySportActivitiesModel.Page.Item.Participant(
                                            urlUserPic = "https://avatars.mds.yandex.net/i?id=06fa91770623ade335e398fbf64916f1_l-12757031-images-thumbs&n=13",
                                        ),
                                        MySportActivitiesModel.Page.Item.Participant(
                                            urlUserPic = "https://avatars.mds.yandex.net/i?id=6f9ec677ac008b86814061aa2eff7daf_l-16457393-images-thumbs&n=13"
                                        ),
                                        MySportActivitiesModel.Page.Item.Participant(
                                            "https://avatars.mds.yandex.net/i?id=402ace28b60f991145720a96a00d0a9b_l-5434761-images-thumbs&n=13"
                                        ),

                                        MySportActivitiesModel.Page.Item.Participant(
                                            "https://bookmaker-ratings.ru/wp-content/uploads/2017/07/1498632318_theboodlestenniseventw0nh96ark6fx.jpg"
                                        ),
                                        MySportActivitiesModel.Page.Item.Participant(
                                            "https://static.independent.co.uk/s3fs-public/thumbnails/image/2017/05/15/20/federer-2009.jpg",
                                        ),
                                        MySportActivitiesModel.Page.Item.Participant(
                                            "https://i.pinimg.com/736x/d3/22/70/d3227097fde0b001068f293c44c2d6fd.jpg",
                                        ),
                                        MySportActivitiesModel.Page.Item.Participant(
                                            "https://mir-s3-cdn-cf.behance.net/project_modules/1400/f47e4851919173.58fe9dc814575.jpg",
                                        ),
                                        MySportActivitiesModel.Page.Item.Participant(
                                            "https://avatars.mds.yandex.net/i?id=34fd61f1d4e2835c3000009ea5c52c64_l-12501487-images-thumbs&n=13",
                                        ),
                                        MySportActivitiesModel.Page.Item.Participant(
                                            "https://avatars.mds.yandex.net/i?id=073e2c1fbf57e194dea705012bc8d930_l-4987768-images-thumbs&n=13",
                                        ),
                                        MySportActivitiesModel.Page.Item.Participant(
                                            "https://bookmaker-ratings.ru/wp-content/uploads/2017/07/1498632318_theboodlestenniseventw0nh96ark6fx.jpg",
                                        )
                                    ),
                                    title = "Бег",
                                    subtitle = "Интервальная тренировка",
                                    description = "18 августа 14:00 \n Крымская ул. 22",
                                    logoIcon = "ic_sport_type_run_24",
                                    logoColor = "type_sport_color_run",
                                    privateStatus = MySportActivitiesModel.Page.Item.PrivateStatus.PRIVATE,
                                    descriptionActivityIcon = "ic_lock_24",
                                    isLiked = false,
                                    isAdd = false,
                                ),
                        MySportActivitiesModel.Page.Item(
                            id = "2",
                            participantList = persistentListOf(
                                MySportActivitiesModel.Page.Item.Participant(
                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=9b20bc9c3d7884532228551baf8c5bd2_l-5219738-images-thumbs&n=13",
                                ),
                                MySportActivitiesModel.Page.Item.Participant(
                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=bf939e1f52344460d5b62b4f75bc8e91_l-5652396-images-thumbs&n=13",
                                ),
                                MySportActivitiesModel.Page.Item.Participant(
                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=06fa91770623ade335e398fbf64916f1_l-12757031-images-thumbs&n=13",
                                ),
                                MySportActivitiesModel.Page.Item.Participant(
                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=6f9ec677ac008b86814061aa2eff7daf_l-16457393-images-thumbs&n=13"
                                ),
                                MySportActivitiesModel.Page.Item.Participant(
                                    "https://avatars.mds.yandex.net/i?id=402ace28b60f991145720a96a00d0a9b_l-5434761-images-thumbs&n=13"
                                )
                            ),
                            logoIcon = "ic_sport_type_run_24",
                            title = "Бег",
                            subtitle = "Интервальная тренировка",
                            descriptionActivityIcon = "ic_lock_24",
                            description = "18 августа 14:00 \n Крымская ул. 22",
                            logoColor = "type_sport_color_run",
                            privateStatus = MySportActivitiesModel.Page.Item.PrivateStatus.OPEN,
                            isLiked = true,
                            isAdd = false,
                        ),
                        MySportActivitiesModel.Page.Item(
                            id = "3",
                            participantList = persistentListOf(
                                MySportActivitiesModel.Page.Item.Participant(
                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=9b20bc9c3d7884532228551baf8c5bd2_l-5219738-images-thumbs&n=13",
                                ),
                                MySportActivitiesModel.Page.Item.Participant(
                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=bf939e1f52344460d5b62b4f75bc8e91_l-5652396-images-thumbs&n=13",
                                ),
                                MySportActivitiesModel.Page.Item.Participant(
                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=06fa91770623ade335e398fbf64916f1_l-12757031-images-thumbs&n=13",
                                ),
                                MySportActivitiesModel.Page.Item.Participant(
                                    urlUserPic = "https://avatars.mds.yandex.net/i?id=6f9ec677ac008b86814061aa2eff7daf_l-16457393-images-thumbs&n=13"
                                ),
                                MySportActivitiesModel.Page.Item.Participant(
                                    "https://avatars.mds.yandex.net/i?id=402ace28b60f991145720a96a00d0a9b_l-5434761-images-thumbs&n=13"
                                )
                            ),
                            logoIcon = "ic_sport_type_run_24",
                            title = "Бег",
                            subtitle = "Интервальная тренировка",
                            description = "18 августа 14:00 \n Крымская ул. 22",
                            logoColor = "type_sport_color_run",
                            privateStatus = MySportActivitiesModel.Page.Item.PrivateStatus.OPEN,
                            descriptionActivityIcon = "ic_lock_24",
                            isLiked = false,
                            isAdd = true,
                        ),
                            MySportActivitiesModel.Page.Item(
                                id = "4",
                                participantList = persistentListOf(
                                    MySportActivitiesModel.Page.Item.Participant(
                                        urlUserPic = "https://avatars.mds.yandex.net/i?id=9b20bc9c3d7884532228551baf8c5bd2_l-5219738-images-thumbs&n=13",
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        urlUserPic = "https://avatars.mds.yandex.net/i?id=bf939e1f52344460d5b62b4f75bc8e91_l-5652396-images-thumbs&n=13",
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        urlUserPic = "https://avatars.mds.yandex.net/i?id=06fa91770623ade335e398fbf64916f1_l-12757031-images-thumbs&n=13",
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        urlUserPic = "https://avatars.mds.yandex.net/i?id=6f9ec677ac008b86814061aa2eff7daf_l-16457393-images-thumbs&n=13"
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        "https://avatars.mds.yandex.net/i?id=402ace28b60f991145720a96a00d0a9b_l-5434761-images-thumbs&n=13"
                                    )
                                ),
                                logoIcon = "ic_sport_type_run_24",
                                title = "Бег",
                                subtitle = "Интервальная тренировка",
                                description = "18 августа 14:00 \n Крымская ул. 22",
                                logoColor = "type_sport_color_run",
                                privateStatus = MySportActivitiesModel.Page.Item.PrivateStatus.OPEN,
                                descriptionActivityIcon = "ic_lock_24",
                                isLiked = false,
                                isAdd = true,
                            )
                    ),
                    ),
                    MySportActivitiesModel.Page(
                        title = "Проведение",
                        items = persistentListOf(
                            MySportActivitiesModel.Page.Item(
                                id = "1",
                                participantList = persistentListOf(
                                    MySportActivitiesModel.Page.Item.Participant(
                                        urlUserPic = "https://avatars.mds.yandex.net/i?id=9b20bc9c3d7884532228551baf8c5bd2_l-5219738-images-thumbs&n=13",
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        urlUserPic = "https://avatars.mds.yandex.net/i?id=bf939e1f52344460d5b62b4f75bc8e91_l-5652396-images-thumbs&n=13",
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        urlUserPic = "https://avatars.mds.yandex.net/i?id=06fa91770623ade335e398fbf64916f1_l-12757031-images-thumbs&n=13",
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        urlUserPic = "https://avatars.mds.yandex.net/i?id=6f9ec677ac008b86814061aa2eff7daf_l-16457393-images-thumbs&n=13"
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        "https://avatars.mds.yandex.net/i?id=402ace28b60f991145720a96a00d0a9b_l-5434761-images-thumbs&n=13"
                                    ),

                                    MySportActivitiesModel.Page.Item.Participant(
                                        "https://bookmaker-ratings.ru/wp-content/uploads/2017/07/1498632318_theboodlestenniseventw0nh96ark6fx.jpg"
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        "https://static.independent.co.uk/s3fs-public/thumbnails/image/2017/05/15/20/federer-2009.jpg",
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        "https://i.pinimg.com/736x/d3/22/70/d3227097fde0b001068f293c44c2d6fd.jpg",
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        "https://mir-s3-cdn-cf.behance.net/project_modules/1400/f47e4851919173.58fe9dc814575.jpg",
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        "https://avatars.mds.yandex.net/i?id=34fd61f1d4e2835c3000009ea5c52c64_l-12501487-images-thumbs&n=13",
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        "https://avatars.mds.yandex.net/i?id=073e2c1fbf57e194dea705012bc8d930_l-4987768-images-thumbs&n=13",
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        "https://bookmaker-ratings.ru/wp-content/uploads/2017/07/1498632318_theboodlestenniseventw0nh96ark6fx.jpg",
                                    )
                                ),
                                title = "Бег",
                                subtitle = "Интервальная тренировка",
                                description = "18 августа 14:00 \n Крымская ул. 22",
                                logoIcon = "ic_sport_type_run_24",
                                logoColor = "type_sport_color_run",
                                privateStatus = MySportActivitiesModel.Page.Item.PrivateStatus.PRIVATE,
                                descriptionActivityIcon = "ic_lock_24",
                                isLiked = false,
                                isAdd = false,
                            ),
                            MySportActivitiesModel.Page.Item(
                                id = "2",
                                participantList = persistentListOf(
                                    MySportActivitiesModel.Page.Item.Participant(
                                        urlUserPic = "https://avatars.mds.yandex.net/i?id=9b20bc9c3d7884532228551baf8c5bd2_l-5219738-images-thumbs&n=13",
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        urlUserPic = "https://avatars.mds.yandex.net/i?id=bf939e1f52344460d5b62b4f75bc8e91_l-5652396-images-thumbs&n=13",
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        urlUserPic = "https://avatars.mds.yandex.net/i?id=06fa91770623ade335e398fbf64916f1_l-12757031-images-thumbs&n=13",
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        urlUserPic = "https://avatars.mds.yandex.net/i?id=6f9ec677ac008b86814061aa2eff7daf_l-16457393-images-thumbs&n=13"
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        "https://avatars.mds.yandex.net/i?id=402ace28b60f991145720a96a00d0a9b_l-5434761-images-thumbs&n=13"
                                    )
                                ),
                                logoIcon = "ic_sport_type_run_24",
                                title = "Бег",
                                subtitle = "Интервальная тренировка",
                                descriptionActivityIcon = "ic_lock_24",
                                description = "18 августа 14:00 \n Крымская ул. 22",
                                logoColor = "type_sport_color_run",
                                privateStatus = MySportActivitiesModel.Page.Item.PrivateStatus.OPEN,
                                isLiked = true,
                                isAdd = false,
                            ),
                            MySportActivitiesModel.Page.Item(
                                id = "3",
                                participantList = persistentListOf(
                                    MySportActivitiesModel.Page.Item.Participant(
                                        urlUserPic = "https://avatars.mds.yandex.net/i?id=9b20bc9c3d7884532228551baf8c5bd2_l-5219738-images-thumbs&n=13",
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        urlUserPic = "https://avatars.mds.yandex.net/i?id=bf939e1f52344460d5b62b4f75bc8e91_l-5652396-images-thumbs&n=13",
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        urlUserPic = "https://avatars.mds.yandex.net/i?id=06fa91770623ade335e398fbf64916f1_l-12757031-images-thumbs&n=13",
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        urlUserPic = "https://avatars.mds.yandex.net/i?id=6f9ec677ac008b86814061aa2eff7daf_l-16457393-images-thumbs&n=13"
                                    ),
                                    MySportActivitiesModel.Page.Item.Participant(
                                        "https://avatars.mds.yandex.net/i?id=402ace28b60f991145720a96a00d0a9b_l-5434761-images-thumbs&n=13"
                                    )
                                ),
                                logoIcon = "ic_sport_type_run_24",
                                title = "Бег",
                                subtitle = "Интервальная тренировка",
                                description = "18 августа 14:00 \n Крымская ул. 22",
                                logoColor = "type_sport_color_run",
                                privateStatus = MySportActivitiesModel.Page.Item.PrivateStatus.OPEN,
                                descriptionActivityIcon = "ic_lock_24",
                                isLiked = false,
                                isAdd = true,
                            )
                        ),
                    )
                )
            )
        )
}