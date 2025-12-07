package com.example.motsi.core.ui.designsystem.divider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.theming.Tokens

@Composable
fun SoftDivider(
    modifier: Modifier = Modifier,
    height: Dp = 5.dp,
    color: Color = Tokens.BackgroundSecondary2.getColor()
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        Color.Transparent,
                        color,
                        Color.Transparent
                    )
                )
            )
    )
}