package com.example.motsi.core.di.holder

inline fun <reified T : Any> getFeatureApi(): T {
    return ComponentRegistry.get(T::class.java).getApi()
}
