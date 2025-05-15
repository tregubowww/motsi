package com.example.motsi.core.di.holder



abstract class InitHolder<T> : Holder<T> {

    @Volatile
    private var api: T? = null
    private val lock = Any()

    protected abstract fun create(): T

    override fun getApi(): T {
        return api ?: synchronized(lock) {
            api ?: create().also { api = it }
        }
    }

    override fun release() {
        synchronized(lock) {
            api = null
        }
    }

    override val isInitialized: Boolean
        get() = api != null
}