package com.example.motsi.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.impl.models.domain.SportActivityDetailsScreenModel
import com.example.motsi.impl.models.domain.SportActivityDetailsScreenModel.Participant
import com.example.motsi.impl.models.domain.SportActivityDetailsScreenModel.PrivateStatus
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

internal class SportActivityDetailsInteractorImpl @Inject constructor(
//    private val repository: SportActivityDetailsRepository
) : SportActivityDetailsInteractor {

    override suspend fun getSportActivityDetailsScreen(id: String): ResultWrapper<SportActivityDetailsScreenModel, MotsiError> =
        ResultWrapper.Success(
            SportActivityDetailsScreenModel(
                typeSport = "Бег",
                typeSportActivity = "Интервалы ",
                typeSportActivityDescription = "3 по 1км по 3:30 через 2 мин трусцы ",
                isFavorites = true,
                dateText = "18 августа 14:00",
                level = SportActivityDetailsScreenModel.Level(text = "3.5", color = "icon_brand1_color"),
                buttons = SportActivityDetailsScreenModel.Buttons(titleChat = "Чат", titleAddSportActivity = "Участвовать"),
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
                        "https://bookmaker-ratings.ru/wp-content/uploads/2017/07/1498632318_theboodlestenniseventw0nh96ark6fx.jpg"),
                    Participant(
                        "https://static.independent.co.uk/s3fs-public/thumbnails/image/2017/05/15/20/federer-2009.jpg",),
                    Participant(
                        "https://i.pinimg.com/736x/d3/22/70/d3227097fde0b001068f293c44c2d6fd.jpg",),
                    Participant(
                        "https://mir-s3-cdn-cf.behance.net/project_modules/1400/f47e4851919173.58fe9dc814575.jpg",),
                    Participant(
                        "https://avatars.mds.yandex.net/i?id=34fd61f1d4e2835c3000009ea5c52c64_l-12501487-images-thumbs&n=13",),
                    Participant(
                        "https://avatars.mds.yandex.net/i?id=073e2c1fbf57e194dea705012bc8d930_l-4987768-images-thumbs&n=13",),
                    Participant(
                        "https://bookmaker-ratings.ru/wp-content/uploads/2017/07/1498632318_theboodlestenniseventw0nh96ark6fx.jpg",)
                ),
                 privateStatus = PrivateStatus.Private(title = "Закрытый", description = "вы можете подать заявку на включение но только создатель активности может вас включиьв активность", icon = "ic_lock_fill_24")
            )
        )
}