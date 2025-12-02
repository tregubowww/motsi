package com.example.motsi.feature.mysportactivities.impl.di

import com.example.motsi.core.di.holder.InitHolder
import com.example.motsi.core.network.di.NetworkHolder


internal object MySportActivitiesHolder : InitHolder<MySportActivitiesInternalApi>() {
    override fun create(): MySportActivitiesInternalApi =
        DaggerMySportActivitiesComponent.factory().create(NetworkHolder.getApi())
}