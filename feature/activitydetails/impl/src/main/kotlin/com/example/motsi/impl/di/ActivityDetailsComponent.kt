package com.example.motsi.impl.di

import com.example.motsi.core.di.FeatureScope
import dagger.Component

@FeatureScope
@Component(modules = [ActivityDetailsModule::class])
internal interface  ActivityDetailsComponent: ActivityDetailsInnerApi
