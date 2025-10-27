package com.example.motsi.core.wrappers.infrastructure

import kotlinx.coroutines.flow.Flow

interface NetworkHelperWrapper {
    fun isInternetAvailable(): Boolean
    val networkStatusFlow: Flow<Boolean> // можно добавлять для реактивного отслеживания
}