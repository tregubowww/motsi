package com.example.motsi.core.ui.theming

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

interface ColorToken : Parcelable {


    @Composable
    @ReadOnlyComposable
    fun getColor(): Color
}