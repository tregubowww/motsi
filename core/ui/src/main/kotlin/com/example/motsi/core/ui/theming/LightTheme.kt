package com.example.motsi.core.ui.theming

import androidx.compose.ui.graphics.Color

fun lightThemeTokensMap(): Map<Tokens, Color> = mapOf(
    Tokens.BackgroundSecondary to Gray05,
    Tokens.Background to White,
    Tokens.BackgroundBrand to Blue2,
    Tokens.BackgroundBrand2 to Lime3,

    Tokens.TextPrimary to Black,
    Tokens.TextPrimaryInverse to White,
    Tokens.TextSecondary to Gray5,
    Tokens.TextBrand to Blue2,

    Tokens.IconBrand to Blue2,
    Tokens.IconBrand2 to Lime3,
    Tokens.IconPrimary to Black,
    Tokens.IconPrimaryReverse to Black,
    Tokens.IconSecondary to Gray5,
    Tokens.IconFavorites to Red4,
)