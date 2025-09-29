package com.example.motsi.core.ui.theming

import androidx.compose.ui.graphics.Color

fun darkThemeTokensMap(): Map<Tokens, Color> = mapOf(
    Tokens.BackgroundSecondary to Graphite2,
    Tokens.Background to Black,
    Tokens.BackgroundBrand to Blue2,
    Tokens.BackgroundBrand2 to Lime3,

    Tokens.TextPrimary to White,
    Tokens.TextPrimaryInverse to White,
    Tokens.TextSecondary to Gray3,
    Tokens.TextBrand to Blue4,

    Tokens.IconBrand to Blue2,
    Tokens.IconBrand2 to Lime3,
    Tokens.IconPrimary to Gray0,
    Tokens.IconPrimaryReverse to White,
    Tokens.IconSecondary to Gray3,
    Tokens.IconFavorites to Red4,
)