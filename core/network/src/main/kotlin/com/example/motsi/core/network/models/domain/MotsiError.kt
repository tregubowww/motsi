package com.example.motsi.core.network.models.domain

import com.example.motsi.core.network.models.data.ErrorResponse


//рефаторинг
sealed class MotsiError(
    override val message: String,
    val title: String = "Ошибка",
    val subTitle: String = "",
    val positiveButton: ErrorButton? = ErrorButton("Понятно", MotsiAction.BackStack),
    val neutralButton: ErrorButton? = null,
    val negativeButton: ErrorButton? = null,
) : Exception(message) {

    data class Network(val raw: String?) : MotsiError(message = raw ?: "Нет интернета")

    data class Unauthorized(val raw: String?) : MotsiError(message = raw ?: "Неавторизован")

    data class NotFound(val raw: String?) : MotsiError(message = raw ?: "Не найдено")

    data class Timeout(val raw: String?) : MotsiError(message = raw ?: "Таймаут")

    data class Server(val raw: String?) : MotsiError(message = raw ?: "Ошибка сервера")

    data class EmptyBody(val raw: String?) : MotsiError(message = raw ?: "Пустое тело ответа")

    data class ApiError(val error: ErrorResponse) : MotsiError(
        message = error.messageText ?: "Неизвестная ошибка",
        title = error.title ?: "Ошибка",
        subTitle = error.subTitle ?: "",
//        positiveButton = error.positiveButton,
//        negativeButton = error.negativeButton,
//        neutralButton = error.neutralButton
    )

    data class UnknownError(val raw: String?) : MotsiError(
        message = raw ?: "Неизвестная ошибка",
        title = "Что-то пошло не так",
        subTitle = "Попробуйте позже",
        positiveButton = ErrorButton("Понятно", MotsiAction.BackStack),
        negativeButton = null,
        neutralButton = null
    )

    data class ErrorButton(
        val title: String,
        val action: MotsiAction
    )

    sealed class MotsiAction {
        data class Deeplink(val deeplink: String) : MotsiAction()
        data object BackStack : MotsiAction()
    }
}