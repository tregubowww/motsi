package com.example.motsi.feature.userprofile.impl.di

import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.network.di.NetworkApi
import com.example.motsi.core.wrappers.di.WrappersCoreApi
import dagger.Component

@FeatureScope
@Component(
    dependencies = [NetworkApi::class, WrappersCoreApi::class],
    modules = [UserProfileModule::class, UserProfileViewModelModule::class]
)
internal interface  UserProfileComponent : UserProfileInternalApi {

    @Component.Factory
    interface Factory {
        fun create(networkApi: NetworkApi, wrappersCoreApi: WrappersCoreApi): UserProfileComponent
    }
}