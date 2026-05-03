package com.example.motsi.core.ui.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class ItemSportActivityButton (
    val icon : Painter,
    val contentDescription : String? = null,
    val tint : Color ,
    val onClick: () -> Unit
)