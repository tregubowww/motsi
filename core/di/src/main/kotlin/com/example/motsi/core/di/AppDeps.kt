package com.example.motsi.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

interface AppDeps  {
    val application: Application
    val context: Context
    val preferences: SharedPreferences
}