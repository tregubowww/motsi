package com.example.motsi.feature.login.impl.data.converter

import com.example.motsi.feature.login.impl.models.data.ValidationUsernameDataModel
import com.example.motsi.feature.login.impl.models.domain.UsernameValidationResult
import javax.inject.Inject

class UsernameConverter @Inject constructor() {

    fun toDomain(validationUsernameDataModel: ValidationUsernameDataModel): UsernameValidationResult =
        UsernameValidationResult(
            errorMessage = validationUsernameDataModel.errorMessage
        )
}