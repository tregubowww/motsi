package com.example.motsi.core.ui.theming

import androidx.compose.ui.graphics.Color

fun lightThemeTokensMap(): Map<Tokens, Color> = mapOf(
    Tokens.Background to White,
    Tokens.TextPrimary to Black,
    Tokens.InverseTextPrimary to White,
    Tokens.ButtonPrimary to Black,
    Tokens.InverseButtonPrimary to White,
    Tokens.TextFieldPrimary to Gray76,
    Tokens.Warning to Red4,
)