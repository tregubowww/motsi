package com.example.motsi.feature.addsportactivity.impl.di

import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.network.di.NetworkApi
import dagger.Component

@FeatureScope
@Component(
    dependencies = [NetworkApi::class, ],
    modules = [AddSportActivityModule::class, AddSportActivityViewModelModule::class]
)
internal interface AddSportActivityComponent : AddSportActivityInternalApi {

    @Component.Factory
    interface Factory {
        fun create(networkApi: NetworkApi): AddSportActivityComponent
    }
}
