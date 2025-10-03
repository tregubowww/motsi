package com.example.motsi.domain

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.models.domain.MainModel

internal interface MainInteractor {
    suspend fun getMainModel(): ResultWrapper<MainModel, MotsiError>
}