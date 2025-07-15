package com.example.motsi.core.ui.theming

@kotlinx.parcelize.Parcelize
enum class Tokens : ColorToken {
    Background,
    BackgroundSecondary,
    BackgroundFont,

    TextPrimary,
    TextSecondary,
    TextBlue,

    IconPrimary,
    IconSecondary,
    IconPrimaryActiv
    ;

    @androidx.compose.runtime.Composable
    @androidx.compose.runtime.ReadOnlyComposable
    override fun getColor(): androidx.compose.ui.graphics.Color = MotsiTheme.tokens.getValue(this)
}