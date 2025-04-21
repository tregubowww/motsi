package com.example.motsi.core.ui.theming

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

object RoundedShapes {
    val small = RoundedCornerShape(8.dp)
}

internal val LocalRoundedShapes = staticCompositionLocalOf { RoundedShapes }

