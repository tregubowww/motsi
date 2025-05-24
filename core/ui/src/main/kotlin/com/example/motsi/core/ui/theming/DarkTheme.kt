package com.example.motsi.core.ui.theming

import androidx.compose.ui.graphics.Color

fun darkThemeTokensMap(): Map<Tokens, Color> = mapOf(
    Tokens.Background to Gray3,
    Tokens.TextPrimary to White,
    Tokens.InverseTextPrimary to Black,
    Tokens.ButtonPrimary to White,
    Tokens.InverseButtonPrimary to Black,
    Tokens.TextFieldPrimary to Gray75,
    Tokens.Warning to Red7,
)