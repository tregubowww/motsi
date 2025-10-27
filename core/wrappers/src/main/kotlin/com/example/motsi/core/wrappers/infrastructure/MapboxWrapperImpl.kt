package com.example.motsi.core.wrappers.infrastructure

import android.content.Context
import android.content.SharedPreferences
import org.osmdroid.config.Configuration

class MapboxWrapperImpl(
   private val context: Context,
   private val preferences: SharedPreferences
) : MapboxWrapper {
    override fun initialize() {
        Configuration.getInstance().load(context, preferences)
        Configuration.getInstance().userAgentValue = context.packageName
    }
}