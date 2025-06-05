package com.example.motsi.core.di.holder

abstract class InitWithDepsHolder<T, D> : Holder<T> {
    @Volatile private var api: T? = null
    private var deps: D? = null
    private val lock = Any()

    fun init(deps: D) {
        synchronized(lock) {
            this.deps = deps
            this.api = create(deps)
        }
    }

    protected abstract fun create(deps: D): T

    override fun getApi(): T = api ?: error("Component not initialized")
    override fun release() = synchronized(lock) { api = null; deps = null }
    override val isInitialized: Boolean get() = api != null
}
