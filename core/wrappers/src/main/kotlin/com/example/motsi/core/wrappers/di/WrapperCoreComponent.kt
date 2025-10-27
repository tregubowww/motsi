package com.example.motsi.core.wrappers.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import android.content.Context
import android.content.SharedPreferences

@Singleton
@Component(modules = [WrappersModule::class])
interface WrapperCoreComponent : WrappersCoreApi {


    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            @BindsInstance context: Context,
            @BindsInstance preferences: SharedPreferences
        ): WrapperCoreComponent
    }
}