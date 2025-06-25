package com.example.motsi

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Send
import com.example.motsi.core.navigation.models.NavBottomBarItem
import com.example.motsi.core.navigation.presentation.FeatureNavEntry
import com.example.motsi.feature.login.api.LoginGraph
import com.example.motsi.feature.search.api.SearchGraph
import com.example.motsi.messages.api.MessagesGraph
import javax.inject.Inject

class NavManager @Inject constructor(
    val featureNavEntrySet: Set<@JvmSuppressWildcards FeatureNavEntry>
){

    val startDestination = LoginGraph //SearchGraph поменять при работе

    fun getNavBottomBarItem ()  = listOf(
        NavBottomBarItem(
            route = SearchGraph,
            icon = Icons.Rounded.Home,
        ),
        NavBottomBarItem(
            route = MessagesGraph,
            icon = Icons.Rounded.Send,
        )
    )

    fun getNavEntry(clazz: Class<out FeatureNavEntry>): FeatureNavEntry {
        return featureNavEntrySet.firstOrNull { clazz.isAssignableFrom(it::class.java) }
            ?: error("Unknown FeatureNavEntry for class ${clazz.simpleName}")
    }
}