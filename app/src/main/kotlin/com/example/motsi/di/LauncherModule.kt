package com.example.motsi.di

import com.example.motsi.core.navigation.presentation.FeatureNavEntry
import com.example.motsi.core.wizard.impl.presentation.WizardNavEntry
import com.example.motsi.feature.mysportactivities.impl.presentation.MySportActivitiesNavEntry
import com.example.motsi.feature.search.impl.presentation.SearchNavEntry
import com.example.motsi.feature.userprofile.impl.presentation.UserProfileNavEntry
import com.example.motsi.impl.presentation.SportActivityDetailsNavEntry
import com.example.motsi.messeges.impl.presentation.MessagesNavEntry
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
    fun messagesLauncher(launcher: MessagesNavEntry): FeatureNavEntry

    @Binds
    @Singleton
    @IntoSet
    fun activityDetailsLauncher(launcher: SportActivityDetailsNavEntry): FeatureNavEntry

    @Binds
    @Singleton
    @IntoSet
    fun userProfileLauncher(launcher: UserProfileNavEntry): FeatureNavEntry

    @Binds
    @Singleton
    @IntoSet
    fun mySportActivitiesLauncher(launcher: MySportActivitiesNavEntry): FeatureNavEntry

    @Binds
    @Singleton
    @IntoSet
    fun wizardLauncher(launcher: WizardNavEntry): FeatureNavEntry
}