package com.example.motsi

import android.app.Application
import com.example.motsi.core.wrappers.di.WrappersCoreHolder
import com.example.motsi.di.AppComponent
import com.example.motsi.di.DaggerAppComponent

class SampleApplication : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
        WrappersCoreHolder.init(appComponent)
    }
}

val Application.appComponent: AppComponent
    get() = (this as SampleApplication).appComponent

