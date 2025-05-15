package com.example.motsi.core.network.data

import com.example.motsi.core.common.data.repository.utils.map
import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Response

interface ApiResponseHandler {
    suspend fun <T : Any> handle(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        timeoutMillis: Long = DEFAULT_TIMEOUT,
        call: suspend () -> Response<T>
    ): ResultWrapper<T, MotsiError>
}

suspend fun <T : Any, R> ApiResponseHandler.requestMapped(
    call: suspend () -> Response<T>,
    mapper: (T) -> R
): ResultWrapper<R, MotsiError> =
    this.handle(call = call).map(mapper)
const val DEFAULT_TIMEOUT = 15000L
