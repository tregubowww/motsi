package com.example.motsi.di

import com.example.motsi.core.navigation.presentation.FeatureNavEntry
import com.example.motsi.feature.search.impl.presentation.SearchNavEntry
import com.example.motsi.impl.presentation.ActivityDetailsNavEntry
import com.example.motsi.messeges.impl.presentation.MessagesLauncher
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
internal interface LauncherModule {

    @Binds
    @Singleton
    @IntoSet
    fun searchLauncher(launcher: SearchNavEntry): FeatureNavEntry

    @Binds
    @Singleton
    @IntoSet
    fun messagesLauncher(launcher: MessagesLauncher): FeatureNavEntry

    @Binds
    @Singleton
    @IntoSet
    fun activityDetailsLauncher(launcher: ActivityDetailsNavEntry): FeatureNavEntry
}