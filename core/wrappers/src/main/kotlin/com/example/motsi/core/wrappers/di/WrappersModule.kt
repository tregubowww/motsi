package com.example.motsi.core.wrappers.di

import android.content.Context
import android.content.SharedPreferences
import com.example.motsi.core.wrappers.infrastructure.LocationHelperWrapper
import com.example.motsi.core.wrappers.infrastructure.LocationHelperWrapperImpl
import com.example.motsi.core.wrappers.infrastructure.MapboxWrapper
import com.example.motsi.core.wrappers.infrastructure.MapboxWrapperImpl
import com.example.motsi.core.wrappers.infrastructure.NetworkHelperWrapper
import com.example.motsi.core.wrappers.infrastructure.NetworkHelperWrapperImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import com.example.motsi.core.wrappers.infrastructure.ResourceManager
import com.example.motsi.core.wrappers.infrastructure.ResourceManagerImpl

@Module
class WrappersModule {

    @Provides
    @Singleton
    fun provideResourceManager(
        context: Context
    ): ResourceManager {
        return ResourceManagerImpl(context)
    }

    @Provides
    @Singleton
    fun provideMapboxWrapper(
        context: Context,
        preferences: SharedPreferences
    ): MapboxWrapper {
        return MapboxWrapperImpl(context = context, preferences = preferences)
    }

    @Provides
    @Singleton
    fun provideLocationHelperWrapper(
        context: Context
    ): LocationHelperWrapper {
        return LocationHelperWrapperImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideNetworkHelperWrapper(
        context: Context
    ): NetworkHelperWrapper {
        return NetworkHelperWrapperImpl(context = context)
    }
}