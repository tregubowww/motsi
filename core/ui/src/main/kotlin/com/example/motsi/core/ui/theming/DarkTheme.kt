package com.example.motsi.core.ui.theming

import androidx.compose.ui.graphics.Color

fun darkThemeTokensMap(): Map<Tokens, Color> = mapOf(
    Tokens.Background to Gray3,
    Tokens.BackgroundSecondary to Graphite2,

    Tokens.TextPrimary to White,
    Tokens.TextSecondary to Gray3,

    Tokens.IconPrimary to Gray0,
    Tokens.IconSecondary to Gray3,
)