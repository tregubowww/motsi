package com.example.motsi.core.wrappers.infrastructure

import androidx.annotation.StringRes

interface ResourceManager {
    fun getString(@StringRes resId: Int): String
    fun getString(@StringRes resId: Int, vararg args: Any): String
}