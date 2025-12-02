package com.example.motsi.feature.mysportactivities.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.mysportactivities.impl.models.domain.MySportActivitiesModel


internal interface MySportActivitiesInteractor {

    suspend fun getDataScreen(): ResultWrapper<MySportActivitiesModel, MotsiError>
}