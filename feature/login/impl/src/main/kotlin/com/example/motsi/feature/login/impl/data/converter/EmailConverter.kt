package com.example.motsi.feature.login.impl.data.converter

import com.example.motsi.feature.login.impl.models.data.ValidationEmailDataModel
import com.example.motsi.feature.login.impl.models.domain.EmailValidationResult
import javax.inject.Inject

class EmailConverter @Inject constructor() {

    fun toDomain(validationEmailDataModel: ValidationEmailDataModel): EmailValidationResult =
        EmailValidationResult(
            errorMessage = validationEmailDataModel.errorMessage
        )
}