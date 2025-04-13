package com.example.motsi.messeges.impl.di

import com.example.motsi.core.common.models.FeatureScope
import dagger.Component

@com.example.motsi.core.common.models.FeatureScope
@Component(modules = [MessagesModule::class])
internal interface  MessagesComponent: MessagesInnerApi
