package com.example.motsi.core.wrappers.di

import android.app.Application
import android.content.Context
import com.example.motsi.core.wrappers.infrastructure.LocationHelperWrapper
import com.example.motsi.core.wrappers.infrastructure.MapboxWrapper
import com.example.motsi.core.wrappers.infrastructure.NetworkHelperWrapper
import com.example.motsi.core.wrappers.infrastructure.ResourceManager


interface WrappersCoreApi {
    fun resourceManager(): ResourceManager
    fun context(): Context
    fun application(): Application
    fun mapboxWrapper(): MapboxWrapper
    fun locationHelperWrapper(): LocationHelperWrapper
    fun networkHelperWrapper(): NetworkHelperWrapper
}