package com.example.motsi.core.ui.utils

import androidx.compose.runtime.Composable
import com.example.motsi.core.ui.theming.Tokens

@Composable
fun String.toColor(): Tokens =
    colors[this] ?: Tokens.Background


private val colors: Map<String, Tokens> = mapOf(
    "icon_primary_color" to Tokens.IconPrimary,
    "icon_brand_color" to Tokens.IconBrand,
    "background_secondary_color" to Tokens.BackgroundSecondary,
    "text_secondary_color" to Tokens.TextSecondary
)