package com.example.motsi.core.ui.theming

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.motsi.core.ui.R

object TextAppearance {
    val Headline1: TextStyle = motsiTextStyle.copy(
        fontFamily = semibold,
        fontSize = TextDimensions.HeadLine1TextSize,
        lineHeight = TextDimensions.Headline2LineHeight,
        letterSpacing = TextDimensions.HeadLine2LetterSpacing
    )
    val Headline2: TextStyle = motsiTextStyle.copy(
        fontFamily = bold,
        fontSize = TextDimensions.HeadLine2TextSize,
        lineHeight = TextDimensions.Headline2LineHeight,
        letterSpacing = TextDimensions.HeadLine2LetterSpacing
    )

    val Headline3: TextStyle = motsiTextStyle.copy(
        fontFamily = semibold,
        fontSize = TextDimensions.HeadLine3TextSize,
        lineHeight = TextDimensions.HeadLine3LineHeight,
        letterSpacing = TextDimensions.HeadLine3LetterSpacing
    )
    val Title1: TextStyle = motsiTextStyle.copy(
        fontFamily = semibold,
        fontSize = TextDimensions.Title1TextSize,
        lineHeight = TextDimensions.Title1LineHeight,
        letterSpacing = TextDimensions.Title1LetterSpacing
    )
    val Title2: TextStyle = motsiTextStyle.copy(
        fontFamily = medium,
        fontSize = TextDimensions.Title2TextSize,
        lineHeight = TextDimensions.Title2LineHeight,
        letterSpacing = TextDimensions.Title2LetterSpacing
    )
    val Title3: TextStyle = motsiTextStyle.copy(
        fontFamily = semibold,
        fontSize = TextDimensions.Title3TextSize,
        lineHeight = TextDimensions.Title3LineHeight,
        letterSpacing = TextDimensions.Title3LetterSpacing
    )
    val Body1: TextStyle = motsiTextStyle.copy(
        fontFamily = medium,
        fontSize = TextDimensions.Body1TextSize,
        lineHeight = TextDimensions.Body1LineHeight,
        letterSpacing = TextDimensions.Body1LetterSpacing,
    )
    val Body2: TextStyle = motsiTextStyle.copy(
        fontFamily = regular,
        fontSize = TextDimensions.Body2TextSize,
        lineHeight = TextDimensions.Body2LineHeight,
        letterSpacing = TextDimensions.Body2LetterSpacing
    )
    val Body3: TextStyle = motsiTextStyle.copy(
        fontFamily = medium,
        fontSize = TextDimensions.Body3TextSize,
        lineHeight = TextDimensions.Body3LineHeight,
        letterSpacing = TextDimensions.Body3LetterSpacing
    )
    val Footnote1: TextStyle = motsiTextStyle.copy(
        fontFamily = medium,
        fontSize = TextDimensions.Footnote1TextSize,
        lineHeight = TextDimensions.Footnote1LineHeight,
        letterSpacing = TextDimensions.Footnote1LetterSpacing
    )

    val Footnote2: TextStyle = motsiTextStyle.copy(
        fontFamily = semibold,
        fontSize = TextDimensions.Footnote2TextSize,
        lineHeight = TextDimensions.Footnote2LineHeight,
        letterSpacing = TextDimensions.Footnote2LetterSpacing
    )
    val Caption: TextStyle = motsiTextStyle.copy(
        fontFamily = medium,
        fontSize = TextDimensions.CaptionTextSize,
        lineHeight = TextDimensions.CaptionLineHeight,
        letterSpacing = TextDimensions.CaptionLetterSpacing
    )
    val SpecialTitle: TextStyle = motsiTextStyle.copy(
        fontFamily = semibold,
        fontSize = TextDimensions.SpecialTitleTextSize,
        lineHeight = TextDimensions.SpecialTitleLineHeight,
        letterSpacing = TextDimensions.SpecialTitleLetterSpacing
    )
    val SpecialBody: TextStyle = motsiTextStyle.copy(
        fontFamily = medium,
        fontSize = TextDimensions.SpecialBodyTextSize,
        lineHeight = TextDimensions.SpecialBodyLineHeight,
        letterSpacing = TextDimensions.SpecialBodyLetterSpacing
    )
    val SpecialCaption: TextStyle = motsiTextStyle.copy(
        fontFamily = medium,
        fontSize = TextDimensions.SpecialCaptionTextSize,
        lineHeight = TextDimensions.SpecialCaptionLineHeight,
        letterSpacing = TextDimensions.SpecialCaptionLetterSpacing
    )
}

internal val LocalTextAppearance = staticCompositionLocalOf { TextAppearance }

private val bold = FontFamily(
    Font(R.font.roboto_bold, FontWeight.Medium)
)

private val medium = FontFamily(
    Font(R.font.roboto_medium, FontWeight.Medium)
)

private val semibold = FontFamily(
    Font(R.font.roboto_semibold, FontWeight.SemiBold)
)


private val regular = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal)
)

private val motsiTextStyle =
    TextStyle(
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Proportional,
            trim = LineHeightStyle.Trim.None
        )
    )