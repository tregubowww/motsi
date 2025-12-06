package com.example.motsi.messeges.impl.di

import com.example.motsi.core.di.FeatureScope
import dagger.Component

@FeatureScope
@Component(modules = [MessagesModule::class, MessagesViewModelModule::class])
internal interface MessagesComponent : MessagesInternalApi {
    @Component.Factory
    interface Factory {
        fun create(): MessagesComponent
    }
}

