package com.example.motsi.core.ui.theming

import androidx.compose.ui.graphics.Color

fun lightThemeTokensMap(): Map<Tokens, Color> = mapOf(
    Tokens.Background to White,
    Tokens.BackgroundBrand to Blue2,
    Tokens.BackgroundBrand2 to Lime3,
    Tokens.BackgroundGray to Gray7,
    Tokens.BackgroundPrimary to Black,
    Tokens.BackgroundSecondary to Gray0,
    Tokens.BackgroundSecondary2 to Gray05,

    Tokens.TextBrand to Blue2,
    Tokens.TextPrimary to Black,
    Tokens.TextPrimaryInverse to White,
    Tokens.TextSecondary to Gray5,

    Tokens.IconBrand1 to Blue2,
    Tokens.IconBrand2 to Lime3,
    Tokens.IconFavorites to Red4,
    Tokens.IconPrimary to Black,
    Tokens.IconWhite to White,
    Tokens.IconPrimaryInverse to White,
    Tokens.IconRating to Yellow1,
    Tokens.IconSecondary to Gray5,
)