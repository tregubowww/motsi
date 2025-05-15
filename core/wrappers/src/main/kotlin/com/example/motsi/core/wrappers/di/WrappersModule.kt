package com.example.motsi.core.wrappers.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import com.example.motsi.core.wrappers.infrastructure.ResourceManager
import com.example.motsi.core.wrappers.infrastructure.ResourceManagerImpl

@Module
class WrappersModule() {

    @Provides
    @Singleton
    fun provideResourceManager( context: Context): ResourceManager {
        return ResourceManagerImpl(context)
    }
}