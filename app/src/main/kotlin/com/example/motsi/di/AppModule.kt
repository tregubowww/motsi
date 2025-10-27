package com.example.motsi.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(app: Application): Context = app.applicationContext ?: app

    @Provides
    @Singleton
    fun provideApplicationPreferences(
        context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(
            "${context.packageName}_preferences",
            Context.MODE_PRIVATE
        )
    }
}