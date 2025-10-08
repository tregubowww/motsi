package com.example.motsi.feature.addsportactivity.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.addsportactivity.impl.models.domain.ChoiceTypeSportModel
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

internal class AddSportActivityInteractorImpl @Inject constructor() :
    AddSportActivityInteractor {

    override suspend fun getChoiceTypeSportScreen(): ResultWrapper<ChoiceTypeSportModel, MotsiError> =
        ResultWrapper.Success(
            ChoiceTypeSportModel(
                title = "Вид спорта",
                searchHint = "Поиск",
                listTypeSport = persistentListOf(
                    ChoiceTypeSportModel.TypeSport(
                        name = "Бег",
                        sportGroup = "Индивидуальный",
                        icon = "ic_filter_24"
                    ),
                    ChoiceTypeSportModel.TypeSport(
                        name = "Футбол",
                        sportGroup = "Командный",
                        icon = "ic_filter_24"
                    ),
                    ChoiceTypeSportModel.TypeSport(
                        name = "Теннис",
                        sportGroup = "Индивидуальный",
                        icon = "ic_filter_24"
                    ),
                    ChoiceTypeSportModel.TypeSport(
                        name = "Бег",
                        sportGroup = "Индивидуальный",
                        icon = "ic_filter_24"
                    ),
                    ChoiceTypeSportModel.TypeSport(
                        name = "Футбол",
                        sportGroup = "Командный",
                        icon = "ic_filter_24"
                    ),
                    ChoiceTypeSportModel.TypeSport(
                        name = "Теннис",
                        sportGroup = "Индивидуальный",
                        icon = "ic_filter_24"
                    ),
                    ChoiceTypeSportModel.TypeSport(
                        name = "Бег",
                        sportGroup = "Индивидуальный",
                        icon = "ic_filter_24"
                    ),
                    ChoiceTypeSportModel.TypeSport(
                        name = "Футбол",
                        sportGroup = "Командный",
                        icon = "ic_filter_24"
                    ),
                    ChoiceTypeSportModel.TypeSport(
                        name = "Теннис",
                        sportGroup = "Индивидуальный",
                        icon = "ic_filter_24"
                    ),
                    ChoiceTypeSportModel.TypeSport(
                        name = "Бег",
                        sportGroup = "Индивидуальный",
                        icon = "ic_filter_24"
                    ),
                    ChoiceTypeSportModel.TypeSport(
                        name = "Футбол",
                        sportGroup = "Командный",
                        icon = "ic_filter_24"
                    ),
                    ChoiceTypeSportModel.TypeSport(
                        name = "Теннис",
                        sportGroup = "Индивидуальный",
                        icon = "ic_filter_24"
                    ),
                    ChoiceTypeSportModel.TypeSport(
                        name = "Бег",
                        sportGroup = "Индивидуальный",
                        icon = "ic_filter_24"
                    ),
                    ChoiceTypeSportModel.TypeSport(
                        name = "Футбол",
                        sportGroup = "Командный",
                        icon = "ic_filter_24"
                    ),
                    ChoiceTypeSportModel.TypeSport(
                        name = "Теннис",
                        sportGroup = "Индивидуальный",
                        icon = "ic_filter_24"
                    )
                )
            )
        )
}