package com.example.motsi.impl.di

import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.network.di.NetworkApi
import dagger.Component

@FeatureScope
@Component(
    dependencies = [NetworkApi::class],
    modules = [SportActivityDetailsModule::class, SportActivityDetailsViewModelModule::class]
)
internal interface SportActivityDetailsComponent : SportActivityDetailsInternalApi {

    @Component.Factory
    interface Factory {
        fun create(networkApi: NetworkApi): SportActivityDetailsComponent
    }
}
