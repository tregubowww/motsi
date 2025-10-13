package com.example.motsi.core.ui.utils

import com.example.motsi.core.ui.R

fun String.toIconRes(): Int =
    icons[this] ?: error("Icon $this not found")


private val icons = mapOf(
    "ic_clock_history_20" to R.drawable.ic_clock_history_outline_24dp,
    "ic_filter_24" to R.drawable.ic_filter_outline_24dp,
    "ic_lock_fill_24" to R.drawable.ic_lock_black_fill_24dp,
    "ic_search_20" to R.drawable.ic_search_outline_24dp,
    "ic_sport_type_run_24" to R.drawable.ic_sport_type_run_fill_24dp
)