package com.example.motsi.core.ui.theming

import androidx.compose.ui.graphics.Color

fun darkThemeTokensMap(): Map<Tokens, Color> = mapOf(
    Tokens.BackgroundSecondary to Graphite2,
    Tokens.Background to Black,

    Tokens.TextPrimary to White,
    Tokens.TextSecondary to Gray3,
    Tokens.TextBrand to Blue4,

    Tokens.IconPrimary to Gray0,
    Tokens.IconPrimaryActiv to White,
    Tokens.IconSecondary to Gray3,
)