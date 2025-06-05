package com.example.motsi.core.di.holder

interface Holder<T> {
    fun getApi(): T
    fun release()
    val isInitialized: Boolean
}
