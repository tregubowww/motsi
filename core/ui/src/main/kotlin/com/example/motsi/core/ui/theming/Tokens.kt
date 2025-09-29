package com.example.motsi.core.ui.theming

@kotlinx.parcelize.Parcelize
enum class Tokens : ColorToken {
    Background,
    BackgroundSecondary,
    BackgroundBrand,
    BackgroundBrand2,

    TextPrimary,
    TextPrimaryInverse,
    TextSecondary,
    TextBrand,

    IconBrand,
    IconBrand2,
    IconPrimary,
    IconPrimaryReverse,
    IconSecondary,
    IconFavorites,
    ;

    @androidx.compose.runtime.Composable
    @androidx.compose.runtime.ReadOnlyComposable
    override fun getColor(): androidx.compose.ui.graphics.Color = MotsiTheme.tokens.getValue(this)
}