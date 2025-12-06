package com.example.motsi.core.ui.designsystem.baseline

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.theming.Tokens

@Composable
fun HorizontalLine(
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp),
        color = Tokens.BackgroundSecondary2.getColor(),
    )
}

@Composable
fun VerticalLine(
    modifier: Modifier = Modifier
) {
    VerticalDivider(
        modifier = modifier
            .fillMaxHeight()
            .width(1.dp),
        color = Tokens.BackgroundSecondary2.getColor(),
    )
}