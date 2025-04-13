package com.example.motsi.di

import com.example.motsi.feature.search.impl.SearchLauncher
import com.example.motsi.impl.presentation.ActivityDetailsLauncher
import com.example.motsi.messeges.impl.presentation.MessagesLauncher

interface NavigationApi  {

    val searchLauncher: SearchLauncher

    val messagesLauncher: MessagesLauncher

    val activityDetailsLauncher: ActivityDetailsLauncher
}