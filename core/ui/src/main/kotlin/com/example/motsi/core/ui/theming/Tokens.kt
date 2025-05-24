package com.example.motsi.core.ui.theming

@kotlinx.parcelize.Parcelize
enum class Tokens : ColorToken {
    Background,

    TextPrimary,

    InverseTextPrimary,

    ButtonPrimary,

    InverseButtonPrimary,

    TextFieldPrimary,

    Warning,

    ;

    @androidx.compose.runtime.Composable
    @androidx.compose.runtime.ReadOnlyComposable
    override fun getColor(): androidx.compose.ui.graphics.Color = MotsiTheme.tokens.getValue(this)
}