package com.example.motsi.core.network.data

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlinx.coroutines.withTimeout

internal class ApiResponseHandlerImpl @Inject constructor(private val errorConverter: ErrorConverter) :
    ApiResponseHandler {

    override suspend fun <T : Any> handle(
        dispatcher: CoroutineDispatcher,
        timeoutMillis: Long,
        call: suspend () -> Response<T>
    ): ResultWrapper<T, MotsiError> {
        return try {
            withTimeout(timeoutMillis) {
                withContext(dispatcher) {
                    val response = call()

                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            ResultWrapper.Success(body)
                        } else {
                            ResultWrapper.Error(MotsiError.EmptyBody("Пустое тело ответа"))
                        }
                    } else {
                        ResultWrapper.Error(errorConverter.convert(response))
                    }
                }
            }
        } catch (e: Throwable) {
            ResultWrapper.Error(errorConverter.convert(e))
        }
    }
}