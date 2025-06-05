package com.example.motsi.core.network.di

import com.example.motsi.core.di.holder.InitHolder
import com.example.motsi.core.wrappers.di.WrappersCoreHolder



object NetworkHolder : InitHolder<NetworkApi>() {
    override fun create(): NetworkApi {
        return DaggerNetworkComponent.factory().create(
            WrappersCoreHolder.getApi()
        )
    }
}
