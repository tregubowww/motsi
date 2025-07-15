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
fun SubtitleBlue(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextBlue.getColor(),
        style = MotsiTheme.textAppearance.Body3,
        maxLines = maxLines,
    )
}

@Composable
fun SubtitlePrimarySmall(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextSecondary.getColor(),
        style = MotsiTheme.textAppearance.Footnote2,
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

@Composable
fun Title1PrimarySmall(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextPrimary.getColor(),
        style = MotsiTheme.textAppearance.Title3,
        maxLines = maxLines,
    )
}