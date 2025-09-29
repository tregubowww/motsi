package com.example.motsi.core.ui.theming

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Subtitle1Primary(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextPrimary.getColor(),
        style = MotsiTheme.textAppearance.Body3,
        maxLines = maxLines,
    )
}

@Composable
fun Subtitle1Secondary(
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
fun Subtitle1PrimaryInverse(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextPrimaryInverse.getColor(),
        style = MotsiTheme.textAppearance.Body3,
        maxLines = maxLines,
    )
}

@Composable
fun Subtitle2Primary(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextPrimary.getColor(),
        style = MotsiTheme.textAppearance.Footnote2,
        maxLines = maxLines,
    )
}

@Composable
fun Subtitle2PrimaryInverse(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextPrimaryInverse.getColor(),
        style = MotsiTheme.textAppearance.Footnote2,
        maxLines = maxLines,
    )
}

@Composable
fun Subtitle2Secondary(
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
fun Title2Primary(
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

@Composable
fun SubtitleBrand(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextBrand.getColor(),
        style = MotsiTheme.textAppearance.Body3,
        maxLines = maxLines,
    )
}