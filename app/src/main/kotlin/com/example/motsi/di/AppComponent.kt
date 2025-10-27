package com.example.motsi.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.motsi.presentation.NavManager
import com.example.motsi.core.di.AppDeps
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, LauncherModule::class, MainViewModelModule::class, MainActivityModule::class])
interface AppComponent : AppDeps {

    fun viewModelFactory(): ViewModelProvider.Factory
    fun navManager(): NavManager

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
