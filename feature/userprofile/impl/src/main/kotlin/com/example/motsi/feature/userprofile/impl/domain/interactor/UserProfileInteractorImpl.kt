package com.example.motsi.feature.userprofile.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.userprofile.impl.models.domain.UserProfileScreenModel
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

internal class UserProfileInteractorImpl @Inject constructor(
//    private val repository: UserProfileRepository
) : UserProfileInteractor {

    override suspend fun getUserProfileScreen(): ResultWrapper<UserProfileScreenModel, MotsiError> =
        ResultWrapper.Success(
            UserProfileScreenModel(
                id = "1",
                sportTypeTitle = "виды спорта",
                sportActivityTitle = "Активностей",
                subscribers = UserProfileScreenModel.Followers(
                    title = "Подписчики",
                    count = "40232323",
                    picList = persistentListOf(
                        UserProfileScreenModel.Pic(
                            id = "1",
                            urlPic = "https://avatars.mds.yandex.net/i?id=9b20bc9c3d7884532228551baf8c5bd2_l-5219738-images-thumbs&n=13"
                        ),
                        UserProfileScreenModel.Pic(
                            id = "2",
                            urlPic = "https://avatars.mds.yandex.net/i?id=bf939e1f52344460d5b62b4f75bc8e91_l-5652396-images-thumbs&n=13"
                        ),
                        UserProfileScreenModel.Pic(
                            id = "3",
                            urlPic = "https://avatars.mds.yandex.net/i?id=06fa91770623ade335e398fbf64916f1_l-12757031-images-thumbs&n=13"
                        ),
                        UserProfileScreenModel.Pic(
                            id = "4",
                            urlPic = "https://avatars.mds.yandex.net/i?id=6f9ec677ac008b86814061aa2eff7daf_l-16457393-images-thumbs&n=13"
                        ),
                        UserProfileScreenModel.Pic(
                            id = "5",
                            urlPic = "https://avatars.mds.yandex.net/i?id=402ace28b60f991145720a96a00d0a9b_l-5434761-images-thumbs&n=13"
                        ),
                        UserProfileScreenModel.Pic(
                            id = "6",
                            urlPic = "https://bookmaker-ratings.ru/wp-content/uploads/2017/07/1498632318_theboodlestenniseventw0nh96ark6fx.jpg"
                        ),
                        UserProfileScreenModel.Pic(
                            id = "7",
                            urlPic = "https://static.independent.co.uk/s3fs-public/thumbnails/image/2017/05/15/20/federer-2009.jpg"
                        ),
                    ),
                ),
                subscriptions = UserProfileScreenModel.Followers(
                    title = "Подписки",
                    count = "335423",
                    picList = persistentListOf(
                        UserProfileScreenModel.Pic(
                            id = "1",
                            urlPic = "https://i.pinimg.com/736x/d3/22/70/d3227097fde0b001068f293c44c2d6fd.jpg"
                        ),
                        UserProfileScreenModel.Pic(
                            id = "2",
                            urlPic = "https://mir-s3-cdn-cf.behance.net/project_modules/1400/f47e4851919173.58fe9dc814575.jpg"
                        ),
                        UserProfileScreenModel.Pic(
                            id = "3",
                            urlPic = "https://avatars.mds.yandex.net/i?id=34fd61f1d4e2835c3000009ea5c52c64_l-12501487-images-thumbs&n=13"
                        ),
                        UserProfileScreenModel.Pic(
                            id = "4",
                            urlPic = "https://avatars.mds.yandex.net/i?id=073e2c1fbf57e194dea705012bc8d930_l-4987768-images-thumbs&n=13"
                        ),
                        UserProfileScreenModel.Pic(
                            id = "5",
                            urlPic = "https://bookmaker-ratings.ru/wp-content/uploads/2017/07/1498632318_theboodlestenniseventw0nh96ark6fx.jpg"
                        ),
                        UserProfileScreenModel.Pic(
                            id = "6",
                            urlPic = "https://static.independent.co.uk/s3fs-public/thumbnails/image/2017/05/15/20/federer-2009.jpg"
                        ),
                        UserProfileScreenModel.Pic(
                            id = "7",
                            urlPic = "https://avatars.mds.yandex.net/i?id=9b20bc9c3d7884532228551baf8c5bd2_l-5219738-images-thumbs&n=13"
                        ),
                        UserProfileScreenModel.Pic(
                            id = "8",
                            urlPic = "https://avatars.mds.yandex.net/i?id=06fa91770623ade335e398fbf64916f1_l-12757031-images-thumbs&n=13"
                        ),
                        UserProfileScreenModel.Pic(
                            id = "9",
                            urlPic = "https://avatars.mds.yandex.net/i?id=402ace28b60f991145720a96a00d0a9b_l-5434761-images-thumbs&n=13"
                        )
                    )
                ),
                userProfilePicList = persistentListOf(
                    UserProfileScreenModel.Pic(
                        id = "1",
                        urlPic = "https://cs4.pikabu.ru/post_img/big/2014/08/08/6/1407484790_1827445811.jpg"
                    ),
                    UserProfileScreenModel.Pic(
                        id = "2",
                        urlPic = "https://storage.kun.uz/source/10/bnZhnUyQDQ-jEHNbGaCUn20Gf8C7IuTN.jpg"
                    ),
                    UserProfileScreenModel.Pic(
                        id = "3",
                        urlPic = "https://img5tv.cdnvideo.ru/webp/shared/files/202209/1_1584463.jpg"
                    ),
                    UserProfileScreenModel.Pic(
                        id = "4",
                        urlPic = "https://i.ytimg.com/vi/v1NSpaPpTSA/maxresdefault.jpg"
                    )
                ),
                sportTypeList = persistentListOf(
                    UserProfileScreenModel.SportType(
                        id = "1",
                        type = "Теннис",
                        count = "234",
                        rating = "3,0",
                        urlPic = "ic_sport_type_run_24"
                    ),
                    UserProfileScreenModel.SportType(
                        id = "2",
                        type = "Волейбол",
                        count = "4",
                        urlPic = "ic_sport_type_run_24"
                    ),
                    UserProfileScreenModel.SportType(
                        id = "4",
                        type = "Спортивное ориентирование",
                        count = "1634",
                        rating = "4,5",
                        urlPic = "ic_sport_type_run_24"
                    )
                ),
                userInformation = UserProfileScreenModel.UserInformation(
                    url = "url",
                    name = "Иван Иванов",
                    phoneNumberTitle = "мобильный",
                    phoneNumberValue = "+7 952 111 1111",
                    usernameTitle = "имя пользователя",
                    usernameValue = "@vaska",
                    birthdayTitle = "день рождения",
                    birthdayValue = "27.12.1988",
                    informationTitle = "о себе",
                    informationValue = "https://kubnews.ru/upload/iblock/044"
                )
            )
        )
}
