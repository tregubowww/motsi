package com.example.motsi.core.common.presentation.validator


/**
 * Типы валидаторов
 *
 * @param value значение
 * @param errorMessage сообщение при невалиднасти
 */
sealed class ValidatorType(
    open val value: String,
    open val errorMessage: String,
) {

    /** Тип валидации по максимальной длине строки */
    data class MaxLength(
        override val value: String,
        override val errorMessage: String,
    ) : ValidatorType(value, errorMessage)

    /** Тип валидации по регулярному выражению */
    data class Regexp(
        override val value: String,
        override val errorMessage: String,
    ) : ValidatorType(value , errorMessage)

    /** Тип валидации обязательного заполнения доля */
    data class Required(
        override val value: String,
        override val errorMessage: String,
    ) : ValidatorType(value, errorMessage)

}