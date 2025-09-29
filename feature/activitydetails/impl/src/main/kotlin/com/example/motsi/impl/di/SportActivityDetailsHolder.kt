package com.example.motsi.impl.di

import com.example.motsi.core.di.holder.InitHolder
import com.example.motsi.core.network.di.NetworkHolder


internal object SportActivityDetailsHolder : InitHolder<SportActivityDetailsInternalApi>() {
    override fun create(): SportActivityDetailsInternalApi =
        DaggerSportActivityDetailsComponent.factory().create(NetworkHolder.getApi())
}