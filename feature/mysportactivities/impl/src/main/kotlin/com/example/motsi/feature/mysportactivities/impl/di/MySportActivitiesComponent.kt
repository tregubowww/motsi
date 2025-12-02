package com.example.motsi.feature.mysportactivities.impl.di

import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.network.di.NetworkApi
import dagger.Component

@FeatureScope
@Component(
    dependencies = [NetworkApi::class, ],
    modules = [MySportActivitiesModule::class, MySportActivitiesViewModelModule::class]
)
internal interface MySportActivitiesComponent : MySportActivitiesInternalApi {

    @Component.Factory
    interface Factory {
        fun create(networkApi: NetworkApi): MySportActivitiesComponent
    }
}
