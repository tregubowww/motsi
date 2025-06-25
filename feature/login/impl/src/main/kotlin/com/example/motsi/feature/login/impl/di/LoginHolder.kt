package com.example.motsi.feature.login.impl.di

import com.example.motsi.core.di.holder.InitHolder
import com.example.motsi.core.network.di.NetworkHolder

internal object LoginHolder : InitHolder<LoginInternalApi>() {
    override fun create(): LoginInternalApi =
        DaggerLoginComponent.factory().create(NetworkHolder.getApi())
}