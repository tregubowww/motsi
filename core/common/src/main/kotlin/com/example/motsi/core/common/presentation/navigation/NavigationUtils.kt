package com.example.motsi.core.common.presentation.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.serialization.decodeArguments
import kotlinx.serialization.serializer

@SuppressLint("RestrictedApi")
inline fun <reified T: Any> NavBackStackEntry.toRouteGraph(): T {
    val typeMap = destination.hierarchy.find { it.hasRoute(T::class) }?.arguments?.mapValues { it.value.type }.orEmpty()
    val bundle = arguments?: Bundle()
    return serializer<T>().decodeArguments(bundle, typeMap)
}