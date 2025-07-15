package com.example.motsi.core.ui.theming

import androidx.compose.ui.graphics.Color

fun lightThemeTokensMap(): Map<Tokens, Color> = mapOf(
    Tokens.Background to White,
    Tokens.BackgroundSecondary to Gray05,
    Tokens.BackgroundFont to White,

    Tokens.TextPrimary to Black,
    Tokens.TextSecondary to Gray5,
    Tokens.TextBlue to Blue2,

    Tokens.IconPrimary to Black,
    Tokens.IconPrimaryActiv to Gray5,
    Tokens.IconSecondary to Gray5,
)