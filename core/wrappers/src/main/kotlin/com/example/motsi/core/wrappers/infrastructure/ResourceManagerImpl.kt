package com.example.motsi.core.wrappers.infrastructure

import android.content.Context

class ResourceManagerImpl(private val context: Context) : ResourceManager {

    override fun getString(resId: Int): String {
        return context.getString(resId)
    }

    override fun getString(resId: Int, vararg args: Any): String {
        return context.getString(resId, *args)
    }
}