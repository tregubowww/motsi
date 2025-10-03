package com.example.motsi.domain

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.models.domain.MainModel
import javax.inject.Inject

internal class MainInteractorImpl @Inject constructor(
//    private val repository: MainRepository
) : MainInteractor {

    override suspend fun getMainModel(): ResultWrapper<MainModel, MotsiError> =
        ResultWrapper.Success(
            MainModel(
                numberFavorites = "5",
                numberMassages = "12+"
            )
        )
}