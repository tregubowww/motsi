package com.example.motsi.feature.login.impl.models.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterScreenDataModel(
    @SerialName("title") val title: String,
    @SerialName("fields") val fields: List<RegisterFieldData>
) {
    @Serializable
    data class RegisterFieldData(
        @SerialName("type") val type: String,
        @SerialName("label") val label: String,
        @SerialName("hint") val hint: String,
        @SerialName("validationRules") val validationRules: List<ValidationRuleData>
    )

    @Serializable
    data class ValidationRuleData(
        @SerialName("pattern") val pattern: String,
        @SerialName("errorMessage") val errorMessage: String
    )
}

@Serializable
data class ValidationRequest(
    @SerialName("value") val value: String,
    @SerialName("current_field") val currentField: String? = null
)

@Serializable
data class ValidationResponse(
    @SerialName("isValid") val isValid: Boolean,
    @SerialName("errors") val errors: List<ValidationErrorData>
) {
    @Serializable
    data class ValidationErrorData(
        @SerialName("code") val code: String,
        @SerialName("message") val message: String
    )
}

@Serializable
data class RegisterRequest(
    @SerialName("username") val username: String,
    @SerialName("email") val email: String,
    @SerialName("password") val password: String
)

@Serializable
data class RegisterResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("userId") val userId: String? = null,
    @SerialName("error") val error: String? = null
)