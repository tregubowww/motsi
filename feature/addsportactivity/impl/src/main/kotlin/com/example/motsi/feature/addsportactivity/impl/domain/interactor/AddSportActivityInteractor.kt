package com.example.motsi.feature.addsportactivity.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.addsportactivity.impl.models.domain.ChoiceTypeSportModel


internal interface AddSportActivityInteractor {

    suspend fun getChoiceTypeSportScreen(): ResultWrapper<ChoiceTypeSportModel, MotsiError>
}