package com.example.motsi.core.ui.theming

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Title1Primary(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Tokens.TextPrimary.getColor(),
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MotsiTheme.textAppearance.Title1,
        maxLines = maxLines,
    )
}

@Composable
fun Headline1Primary(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Tokens.TextPrimary.getColor(),
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MotsiTheme.textAppearance.Headline1,
        maxLines = maxLines,
    )
}

@Composable
fun Body1Primary(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Tokens.TextPrimary.getColor(),
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MotsiTheme.textAppearance.Body1,
        maxLines = maxLines,
    )
}

@Composable
fun Body1Brand(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Tokens.TextPrimary.getColor(),
    maxLines: Int = 1,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MotsiTheme.textAppearance.Body1,
        maxLines = maxLines,
    )
}

@Composable
fun Body1Inverse(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Tokens.ButtonPrimary.getColor(),
    maxLines: Int = 1,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MotsiTheme.textAppearance.Body1,
        maxLines = maxLines,
    )
}

@Composable
fun Text1Warning(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Tokens.Warning.getColor(),
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MotsiTheme.textAppearance.Body1,
        maxLines = maxLines,
    )
}
