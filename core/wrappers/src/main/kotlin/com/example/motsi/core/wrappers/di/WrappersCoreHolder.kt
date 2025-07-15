package com.example.motsi.core.wrappers.di

import com.example.motsi.core.di.AppDeps
import com.example.motsi.core.di.holder.InitWithDepsHolder

object WrappersCoreHolder : InitWithDepsHolder<WrappersCoreApi, AppDeps>() {

    override fun create(deps: AppDeps): WrappersCoreApi {
        return DaggerWrapperCoreComponent.factory().create(
            application = deps.application
        )
    }
}

