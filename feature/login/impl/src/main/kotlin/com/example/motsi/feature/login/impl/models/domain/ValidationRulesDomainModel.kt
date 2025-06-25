package com.example.motsi.feature.login.impl.models.domain

import com.example.motsi.feature.login.impl.presentation.view_model.FieldType

/** Доменная модель экрана регистрации */
internal data class RegisterScreenDomainModel(
    val title: String,
    val fields: List<RegisterField>
) {
    data class RegisterField(
        val type: FieldType,
        val label: String,
        val hint: String,
        val validationRules: List<ValidationRule>
    )

    data class ValidationRule(
        val pattern: String,
        val errorMessage: String
    )
}

/** Доменная модель ответа валидации */
internal data class ValidationResultDomain(
    val isValid: Boolean,
    val errors: List<ValidationErrorDomain>
)

/** Доменная модель ошибки валидации */
sealed class ValidationErrorDomain {
    data class UsernameError(val message: String) : ValidationErrorDomain()
    data class EmailError(val message: String) : ValidationErrorDomain()
    data class PasswordError(val message: String) : ValidationErrorDomain()
}