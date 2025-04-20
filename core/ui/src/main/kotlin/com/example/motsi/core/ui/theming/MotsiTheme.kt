package com.example.motsi.core.ui.theming

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

object MotsiTheme {
    val tokens: Map<Tokens, Color>
        @Composable
        @ReadOnlyComposable
        get() = LocalTokens.current

    val textAppearance: TextAppearance
        @Composable
        @ReadOnlyComposable
        get() = LocalTextAppearance.current

    val roundedShapes: RoundedShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalRoundedShapes.current
}

val LocalTokens: ProvidableCompositionLocal<Map<Tokens, Color>> =
    staticCompositionLocalOf { lightThemeTokensMap() }



@Composable
fun MotsiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val map = if(darkTheme) darkThemeTokensMap() else lightThemeTokensMap()

    CompositionLocalProvider(
        LocalTokens provides map
    ) {
        content()
    }
}