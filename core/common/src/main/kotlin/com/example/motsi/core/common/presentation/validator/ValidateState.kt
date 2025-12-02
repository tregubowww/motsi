package com.example.motsi.core.common.presentation.validator

/**
 * Состояние валидации
 *
 * @param value значение
 */
sealed class ValidateState(open val value: String) {

    /** Состояние валидно */
    data class Valid(override val value: String) : ValidateState(value)


    /**
     * Состояние невалидно
     *
     * @param errorMessage сообщение ошибки валидации
     */
    data class Invalid(override val value: String, val errorMessage: String) : ValidateState(value)
}