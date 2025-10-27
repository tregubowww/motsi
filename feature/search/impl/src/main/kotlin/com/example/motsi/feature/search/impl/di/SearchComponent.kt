package com.example.motsi.feature.search.impl.di

import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.network.di.NetworkApi
import com.example.motsi.core.wrappers.di.WrappersCoreApi
import dagger.Component

@FeatureScope
@Component(
    dependencies = [NetworkApi::class, WrappersCoreApi::class],
    modules = [SearchModule::class, SearchViewModelModule::class]
)
internal interface  SearchComponent : SearchInternalApi {

    @Component.Factory
    interface Factory {
        fun create(networkApi: NetworkApi, wrappersCoreApi: WrappersCoreApi): SearchComponent
    }
}
