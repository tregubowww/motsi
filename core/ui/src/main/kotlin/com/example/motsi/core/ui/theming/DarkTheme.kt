package com.example.motsi.core.ui.theming

import androidx.compose.ui.graphics.Color

fun darkThemeTokensMap(): Map<Tokens, Color> = mapOf(
    Tokens.Background to Black,
    Tokens.BackgroundBrand to Blue2,
    Tokens.BackgroundBrand2 to Lime3,
    Tokens.BackgroundPrimary to White,
    Tokens.BackgroundSecondary to Graphite1,
    Tokens.BackgroundSecondary2 to Graphite2,

    Tokens.TextBrand to Blue4,
    Tokens.TextPrimary to White,
    Tokens.TextPrimaryInverse to White,
    Tokens.TextSecondary to Gray3,

    Tokens.IconBrand1 to Blue2,
    Tokens.IconBrand2 to Lime3,
    Tokens.IconFavorites to Red4,
    Tokens.IconPrimary to Gray0,
    Tokens.IconPrimaryReverse to White,
    Tokens.IconSecondary to Gray3,
)