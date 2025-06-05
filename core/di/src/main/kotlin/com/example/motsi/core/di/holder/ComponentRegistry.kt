package com.example.motsi.core.di.holder

object ComponentRegistry {
    private val map = mutableMapOf<Class<*>, Holder<*>>()

    fun <T : Any> register(apiClass: Class<T>, holder: Holder<T>) {
        map[apiClass] = holder
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(apiClass: Class<T>): Holder<T> {
        return map[apiClass] as? Holder<T>
            ?: error("No Holder registered for ${apiClass.simpleName}")
    }

    fun <T : Any> isRegistered(apiClass: Class<T>): Boolean {
        return map.containsKey(apiClass)
    }

    fun clear() {
        map.clear()
    }

    fun releaseAll() {
        map.values.forEach { it.release() }
    }
}