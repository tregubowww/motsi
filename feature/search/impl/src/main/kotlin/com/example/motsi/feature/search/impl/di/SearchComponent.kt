package com.example.motsi.feature.search.impl.di

import com.example.motsi.core.common.models.FeatureScope
import dagger.Component

@com.example.motsi.core.common.models.FeatureScope
@Component(modules = [SearchModule::class])
internal interface  SearchComponent: SearchInnerApi {


}
