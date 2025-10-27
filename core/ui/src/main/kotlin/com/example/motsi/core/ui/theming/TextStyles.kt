package com.example.motsi.core.ui.theming

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun Body3Primary(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis

) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextPrimary.getColor(),
        style = MotsiTheme.textAppearance.Body3,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Body3Secondary(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis

) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextSecondary.getColor(),
        style = MotsiTheme.textAppearance.Body3,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Body3PrimaryInverse(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextPrimaryInverse.getColor(),
        style = MotsiTheme.textAppearance.Body3,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Body3Brand(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextBrand.getColor(),
        style = MotsiTheme.textAppearance.Body3,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Body2Secondary(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.Background.getColor(),
        style = MotsiTheme.textAppearance.Body2,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Footnote2Secondary(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis

) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextPrimary.getColor(),
        style = MotsiTheme.textAppearance.Footnote2,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Footnote2Primary(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextPrimary.getColor(),
        style = MotsiTheme.textAppearance.Footnote2,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Footnote1Primary(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis

) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextSecondary.getColor(),
        style = MotsiTheme.textAppearance.Footnote1,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Footnote2PrimaryInverse(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextPrimaryInverse.getColor(),
        style = MotsiTheme.textAppearance.Footnote2,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun SpecialCaptionPrimaryInverse(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextPrimaryInverse.getColor(),
        style = MotsiTheme.textAppearance.SpecialCaption,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Title1Primary(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis

) {
    Text(
        text = text,
        modifier = modifier,
        color = Tokens.TextPrimary.getColor(),
        style = MotsiTheme.textAppearance.Title1,
        maxLines = maxLines,
        overflow = overflow
    )
}