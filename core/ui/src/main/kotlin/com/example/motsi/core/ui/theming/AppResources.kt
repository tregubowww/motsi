package com.example.motsi.core.ui.theming

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.motsi.core.ui.R

object AppResources {

    private val icons = mapOf(
        "ic_clock_history" to R.drawable.ic_clock_history_24dp,
        "ic_filter" to R.drawable.ic_filter_24dp,
        "ic_lock_fill" to R.drawable.ic_lock_black_fill_24dp,
        "ic_search" to R.drawable.ic_search_24dp,
        "ic_sport_type_run" to R.drawable.ic_sport_type_run_fill_24dp
    )

    private fun String.normalizeIcon(): String =
        this.substringBeforeLast("_20").substringBeforeLast("_24")

    @DrawableRes
    fun iconRes(name: String): Int =
        icons[name.normalizeIcon()] ?: R.drawable.ic_question_24dp

    @Composable
    fun icon(name: String): Painter {
        val resId = remember(name) { iconRes(name) }
        return painterResource(resId)
    }

    private val colors = mapOf(
        "icon_primary_color" to Tokens.IconPrimary,
        "icon_brand1_color" to Tokens.IconBrand1,
        "background_secondary_color" to Tokens.BackgroundSecondary,
        "text_secondary_color" to Tokens.TextSecondary
    )

    private fun String.normalizeColor(): String = this.lowercase()

    @Composable
    fun color(name: String): Color {
        val token = remember(name) {
            colors[name.normalizeColor()] ?: Tokens.Background
        }
        return token.getColor()
    }
}