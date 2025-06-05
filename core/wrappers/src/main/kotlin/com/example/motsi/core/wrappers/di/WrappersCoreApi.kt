package com.example.motsi.core.wrappers.di

import com.example.motsi.core.wrappers.infrastructure.ResourceManager


interface WrappersCoreApi {
    fun resourceManager(): ResourceManager
}