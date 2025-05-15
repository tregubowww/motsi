package com.example.motsi.core.ui.designsystem.appbar.searchappbar

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class AppBarAction(
    @DrawableRes val iconRes: Int,
    val iconTint: Color,
    val iconContentDescription: String,
    val onClick: () -> Unit
)