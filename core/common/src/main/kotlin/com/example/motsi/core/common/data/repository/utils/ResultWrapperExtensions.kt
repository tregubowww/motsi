package com.example.motsi.core.common.data.repository.utils

import com.example.motsi.core.common.models.data.ResultWrapper

inline fun <T, E, R> ResultWrapper<T, E>.map(transform: (T) -> R): ResultWrapper<R, E> {
    return when (this) {
        is ResultWrapper.Success -> ResultWrapper.Success(transform(value))
        is ResultWrapper.Error -> ResultWrapper.Error(error)
    }
}