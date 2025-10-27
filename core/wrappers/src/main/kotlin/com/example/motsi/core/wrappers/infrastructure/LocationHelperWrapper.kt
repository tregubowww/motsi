package com.example.motsi.core.wrappers.infrastructure

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationHelperWrapper {
    fun hasLocationPermission(): Boolean
    suspend fun getCurrentLocationOrNull(): Location?
    fun locationEnabledFlow(pollIntervalMs: Long = 1000L): Flow<Boolean>
    fun isLocationEnabled(): Boolean
}