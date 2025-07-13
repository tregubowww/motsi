package com.example.motsi.feature.login.impl.data.converter

import com.example.motsi.feature.login.impl.models.data.ValidationPasswordDataModel
import com.example.motsi.feature.login.impl.models.domain.PasswordValidationResult
import javax.inject.Inject

class PasswordConverter @Inject constructor() {

    fun toDomain(validationPasswordDataModel: ValidationPasswordDataModel): PasswordValidationResult =
        PasswordValidationResult(
            errorMessage = validationPasswordDataModel.errorMessage
        )
}