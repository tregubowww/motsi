package com.example.motsi.core.navigation.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.serialization.decodeArguments
import com.example.motsi.core.di.holder.ComponentRegistry
import com.example.motsi.core.di.holder.Holder
import kotlinx.serialization.serializer

@SuppressLint("RestrictedApi")
inline fun <reified T : Any> NavBackStackEntry.toRouteGraph(): T {
    val typeMap =
        destination.hierarchy.find { it.hasRoute(T::class) }?.arguments?.mapValues { it.value.type }
            .orEmpty()
    val bundle = arguments ?: Bundle()
    return serializer<T>().decodeArguments(bundle, typeMap)
}

inline fun <reified T : Any> NavGraphBuilder.featureEntry(
    holder: Holder<T>,
    crossinline content: NavGraphBuilder.() -> Unit
) {
    val apiClass = T::class.java

    // Регистрация холдера в реестр, если ещё нет
    if (!ComponentRegistry.isRegistered(apiClass)) {
        ComponentRegistry.register(apiClass, holder)
    }

    // Прокидываем саму навигацию
    this.content()
}