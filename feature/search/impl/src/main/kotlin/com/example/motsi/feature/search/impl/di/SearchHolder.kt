package com.example.motsi.feature.search.impl.di

import com.example.motsi.core.di.holder.InitHolder
import com.example.motsi.core.network.di.NetworkHolder


internal object SearchHolder : InitHolder<SearchInternalApi>() {
    override fun create(): SearchInternalApi =
        DaggerSearchComponent.factory().create(NetworkHolder.getApi())
}