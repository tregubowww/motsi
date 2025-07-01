package com.example.motsi.core.wrappers.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import com.example.motsi.core.wrappers.infrastructure.ResourceManager
import com.example.motsi.core.wrappers.infrastructure.ResourceManagerImpl

@Module
class WrappersModule() {

    @Provides
    @Singleton
    fun provideResourceManager( application: Application): ResourceManager {
        return ResourceManagerImpl(application)
    }
}