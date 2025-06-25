package com.example.motsi.feature.login.impl.data.converter

import com.example.motsi.feature.login.impl.models.data.RegisterResponse
import com.example.motsi.feature.login.impl.models.data.RegisterScreenDataModel
import com.example.motsi.feature.login.impl.models.data.ValidationResponse
import com.example.motsi.feature.login.impl.models.domain.RegisterScreenDomainModel
import com.example.motsi.feature.login.impl.models.domain.ValidationErrorDomain
import com.example.motsi.feature.login.impl.models.domain.ValidationResultDomain
import com.example.motsi.feature.login.impl.presentation.view_model.FieldType
import com.example.motsi.feature.login.impl.presentation.view_model.RegistrationResult
import javax.inject.Inject

internal class RegisterScreenConverter @Inject constructor() {

    fun toDomain(data: RegisterScreenDataModel): RegisterScreenDomainModel {
        return with(data) {
            RegisterScreenDomainModel(
                title = title,
                fields = fields.map { field ->
                    RegisterScreenDomainModel.RegisterField(
                        type = when (field.type) {
                            "username" -> FieldType.USERNAME
                            "email" -> FieldType.EMAIL
                            "password" -> FieldType.PASSWORD1
                            else -> throw IllegalArgumentException("Unknown field type")
                        },
                        label = field.label,
                        hint = field.hint,
                        validationRules = field.validationRules.map { rule ->
                            RegisterScreenDomainModel.ValidationRule(
                                pattern = rule.pattern,
                                errorMessage = rule.errorMessage
                            )
                        }
                    )
                }
            )
        }
    }
}

internal class ValidationConverter @Inject constructor() {

    fun toDomain(response: ValidationResponse): ValidationResultDomain {
        return ValidationResultDomain(
            isValid = response.isValid,
            errors = response.errors.map { error ->
                when (error.code) {
                    "username_error" -> ValidationErrorDomain.UsernameError(error.message)
                    "email_error" -> ValidationErrorDomain.EmailError(error.message)
                    "password_error" -> ValidationErrorDomain.PasswordError(error.message)
                    else -> ValidationErrorDomain.UsernameError("Unknown error")
                }
            }
        )
    }
}

internal class RegistrationConverter @Inject constructor() {

    fun toDomain(response: RegisterResponse): RegistrationResult {
        return if (response.success) {
            RegistrationResult.Success(response.userId ?: "")
        } else {
            RegistrationResult.Error(response.error ?: "Unknown error")
        }
    }
}