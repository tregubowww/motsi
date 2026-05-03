package com.example.motsi.core.wrappers.infrastructure

import android.location.Location

interface LocationHelperWrapper {
    suspend fun getCurrentLocationOrNull(): Location?
    fun isLocationEnabled(): Boolean
}