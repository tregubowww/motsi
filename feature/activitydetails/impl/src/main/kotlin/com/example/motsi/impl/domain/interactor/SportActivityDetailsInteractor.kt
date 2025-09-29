package com.example.motsi.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.impl.models.domain.SportActivityDetailsScreenModel

internal interface SportActivityDetailsInteractor {

    suspend fun getSportActivityDetailsScreen(id: String): ResultWrapper<SportActivityDetailsScreenModel, MotsiError>
}