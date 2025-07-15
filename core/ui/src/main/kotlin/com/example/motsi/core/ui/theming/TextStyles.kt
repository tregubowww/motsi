package com.example.motsi.core.ui.theming

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SubtitlePrimary(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextSecondary.getColor(),
        style = MotsiTheme.textAppearance.Body3,
        maxLines = maxLines,
    )
}

@Composable
fun Title1Primary(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextPrimary.getColor(),
        style = MotsiTheme.textAppearance.Title1,
        maxLines = maxLines,
    )
}