package com.example.motsi.core.wrappers.di

import android.app.Application
import com.example.motsi.core.wrappers.infrastructure.ResourceManager


interface WrappersCoreApi {
    fun resourceManager(): ResourceManager
    fun application(): Application
}