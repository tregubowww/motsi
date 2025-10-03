package com.example.motsi.presentation

import com.example.motsi.core.navigation.presentation.FeatureNavEntry
import com.example.motsi.feature.search.api.SearchGraph
import javax.inject.Inject

class NavManager @Inject constructor(
    val featureNavEntrySet: Set<@JvmSuppressWildcards FeatureNavEntry>
){

    val startDestination = SearchGraph

    fun getNavEntry(clazz: Class<out FeatureNavEntry>): FeatureNavEntry {
        return featureNavEntrySet.firstOrNull { clazz.isAssignableFrom(it::class.java) }
            ?: error("Unknown FeatureNavEntry for class ${clazz.simpleName}")
    }
}