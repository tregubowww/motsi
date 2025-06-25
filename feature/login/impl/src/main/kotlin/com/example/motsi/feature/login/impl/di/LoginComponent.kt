package com.example.motsi.feature.login.impl.di

import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.network.di.NetworkApi
import dagger.Component

@FeatureScope
@Component(
    dependencies = [NetworkApi::class, ],
    modules = [LoginModule::class, LoginViewModelModule::class]
)
internal interface LoginComponent : LoginInternalApi {

    @Component.Factory
    interface Factory {
        fun create(networkApi: NetworkApi): LoginComponent
    }
}
