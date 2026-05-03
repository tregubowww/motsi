package com.example.motsi.core.ui.theming

@kotlinx.parcelize.Parcelize
enum class Tokens : ColorToken {
    Background,
    Transparent,
    BackgroundBrand,
    BackgroundBrandChip,
    BackgroundBrand2,
    BackgroundPrimary,
    BackgroundSecondary,
    BackgroundSecondary2,

    TextBrand,
    TextPrimary,
    TextPrimaryInverse,
    TextSecondary,

    IconBrand1,
    IconBrand2,
    IconFavorites,
    IconPrimary,
    IconWhite,
    IconPrimaryInverse,
    IconRating,
    IconSecondary,
    ;

    @androidx.compose.runtime.Composable
    @androidx.compose.runtime.ReadOnlyComposable
    override fun getColor(): androidx.compose.ui.graphics.Color = MotsiTheme.tokens.getValue(this)
}