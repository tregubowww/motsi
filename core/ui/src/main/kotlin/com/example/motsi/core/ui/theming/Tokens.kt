package com.example.motsi.core.ui.theming

@kotlinx.parcelize.Parcelize
enum class Tokens : ColorToken {
    Background,
    BackgroundSecondary,

    TextPrimary,
    TextSecondary,
    TextBrand,

    IconPrimary,
    //Поменять название
    IconPrimaryActiv,
    IconSecondary
    ;

    @androidx.compose.runtime.Composable
    @androidx.compose.runtime.ReadOnlyComposable
    override fun getColor(): androidx.compose.ui.graphics.Color = MotsiTheme.tokens.getValue(this)
}