package com.example.motsi.feature.login.impl.data.converter

import com.example.motsi.feature.login.impl.models.data.RegisterDataModel
import com.example.motsi.feature.login.impl.models.domain.RegistrationResult
import javax.inject.Inject

class RegistrationConverter @Inject constructor() {

    fun toDomain(registerDataModel: RegisterDataModel): RegistrationResult =
        RegistrationResult(
            isSuccess = registerDataModel.isSuccess,
            userId = registerDataModel.userId
        )
}