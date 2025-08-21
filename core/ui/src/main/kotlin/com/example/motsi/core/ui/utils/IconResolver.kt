package com.example.motsi.core.ui.utils

import com.example.motsi.core.ui.R

fun String.toIconRes(): Int =
    icons[this] ?: error("Icon $this not found")


private val icons = mapOf(
    "ic_search_20" to R.drawable.ic_search_20dp,
    "ic_filter_24" to R.drawable.ic_filter_24dp,
    "ic_clock_history_20" to R.drawable.ic_clock_history_20dp
)