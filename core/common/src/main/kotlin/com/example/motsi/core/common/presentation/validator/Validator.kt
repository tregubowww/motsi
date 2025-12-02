package com.example.motsi.core.common.presentation.validator

import java.util.regex.Pattern

/**
 * Метод валидации
 *
 * @param value значение
 * @param listValidatorType список типов валидаторов
 */
fun List<ValidatorType>.validate(value: String): ValidateState {
    var validateResult: ValidateState = ValidateState.Valid(value)
    this.forEach { validator ->
        validateResult = when (validator) {
            is ValidatorType.Regexp -> validateRegexp(value, validator)
            is ValidatorType.Required -> validateIsEmpty(value, validator)
            is ValidatorType.MaxLength -> validateMaxLength(value, validator)
        }
    }
    if (validateResult is ValidateState.Invalid) return validateResult
    return validateResult
}

private fun validateRegexp(
    value: String,
    validator: ValidatorType.Regexp,
): ValidateState {
    val pattern: Pattern = Pattern.compile(validator.value)
    return if (pattern.matcher(value).find()) {
        ValidateState.Valid(value)
    } else {
        ValidateState.Invalid(value, validator.errorMessage)
    }
}

private fun validateMaxLength(
    value: String,
    validator: ValidatorType
): ValidateState {
    val maxLength = validator.value.toIntOrNull()
    return if (maxLength != null && value.length <= maxLength) {
        ValidateState.Valid(value)
    } else {
        ValidateState.Invalid(value, validator.errorMessage)
    }
}

private fun validateIsEmpty(
    value: String,
    validator: ValidatorType
): ValidateState =
    if (value.isEmpty()) {
        ValidateState.Invalid(
            value,
            validator.errorMessage
        )
    } else {
        ValidateState.Valid(
            value
        )
    }

