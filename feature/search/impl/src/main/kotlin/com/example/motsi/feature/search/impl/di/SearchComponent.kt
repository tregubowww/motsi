package com.example.motsi.feature.search.impl.di

import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.network.di.NetworkApi
import dagger.Component

@FeatureScope
@Component(
    dependencies = [NetworkApi::class, ],
    modules = [SearchModule::class, SearchViewModelModule::class]
)
internal interface SearchComponent : SearchInternalApi {

    @Component.Factory
    interface Factory {
        fun create(networkApi: NetworkApi): SearchComponent
    }
}
