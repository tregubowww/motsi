package com.example.motsi.core.network.di

import com.example.motsi.core.wrappers.di.WrappersCoreApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [WrappersCoreApi::class], modules = [NetworkModule::class])
interface NetworkComponent : NetworkApi {

    @Component.Factory
    interface Factory {
        fun create(wrappersCoreApi: WrappersCoreApi): NetworkComponent
    }
}