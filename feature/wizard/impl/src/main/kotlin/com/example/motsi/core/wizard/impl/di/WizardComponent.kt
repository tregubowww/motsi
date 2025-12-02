package com.example.motsi.core.wizard.impl.di

import com.example.motsi.core.di.FeatureScope
import com.example.motsi.core.network.di.NetworkApi
import dagger.Component

@FeatureScope
@Component(
    dependencies = [NetworkApi::class, ],
    modules = [WizardModule::class, WizardViewModelModule::class]
)
internal interface WizardComponent : WizardInternalApi {

    @Component.Factory
    interface Factory {
        fun create(networkApi: NetworkApi): WizardComponent
    }
}
