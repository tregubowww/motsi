package com.example.motsi.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.motsi.NavManager
import com.example.motsi.core.di.AppDeps
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [LauncherModule::class, AppViewModelModule::class])
interface AppComponent: AppDeps {

    fun viewModelFactory(): ViewModelProvider.Factory
    fun navManager(): NavManager

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
