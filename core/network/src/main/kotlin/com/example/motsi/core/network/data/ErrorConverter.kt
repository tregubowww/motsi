package com.example.motsi.core.network.data

import com.example.motsi.core.wrappers.infrastructure.ResourceManager
import com.example.motsi.core.network.models.data.ErrorResponse
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.core.network.R
import javax.inject.Inject
import kotlinx.serialization.json.Json
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class ErrorConverter @Inject constructor(
    private val resourceManager: ResourceManager
) {

    fun convert(response: Response<*>): MotsiError {
        return try {
            val errorJson = response.errorBody()?.string()
            resourceManager.getString(R.string.error_server_message)
            if (!errorJson.isNullOrBlank()) {
                val parsedError = Json.decodeFromString<ErrorResponse>(errorJson)
                MotsiError.ApiError(parsedError)
            } else {
                when (response.code()) {
                    401 -> MotsiError.Unauthorized("Неавторизован")
                    404 -> MotsiError.NotFound("Не найдено")
                    in 500..599 -> MotsiError.Server("Ошибка сервера")
                    else -> MotsiError.UnknownError("Ошибка: ${response.code()} ${response.message()}")
                }
            }
        } catch (e: Exception) {
            MotsiError.UnknownError("Ошибка при парсинге тела ответа: ${e.message}")
        }
    }

    fun convert(throwable: Throwable): MotsiError {
        return when (throwable) {
            is IOException -> MotsiError.Network("Нет интернета: ${throwable.message}")
            is SocketTimeoutException -> MotsiError.Timeout("Таймаут: ${throwable.message}")
            is MotsiError -> throwable
            else -> MotsiError.UnknownError("Неизвестная ошибка: ${throwable.message}")
        }
    }
}