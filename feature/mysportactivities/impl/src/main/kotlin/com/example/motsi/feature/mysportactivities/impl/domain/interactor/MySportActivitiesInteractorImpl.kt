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
                addSportActivity = MySportActivitiesModel.AddSportActivity(
                    bottomBarButtonTitle = "Добавить активность",
                    urlStartWizardAddSportActivity = "urlStartWizardAddSportActivity"
                ),
                pageList = persistentListOf(
                    MySportActivitiesModel.Page(
                        title = "title"
                    ),
                    MySportActivitiesModel.Page(
                        title = "title"
                    )
                )
            )
        )
}