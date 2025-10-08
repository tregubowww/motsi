package com.example.motsi.feature.addsportactivity.impl.di

import com.example.motsi.core.di.holder.InitHolder
import com.example.motsi.core.network.di.NetworkHolder


internal object AddSportActivityHolder : InitHolder<AddSportActivityInternalApi>() {
    override fun create(): AddSportActivityInternalApi =
        DaggerAddSportActivityComponent.factory().create(NetworkHolder.getApi())
}