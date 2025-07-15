package com.example.motsi.core.ui.utils

import com.example.motsi.core.ui.R

fun String.toIconRes(): Int =
    drawable[this] ?: error("Drawable $this not found")


private val drawable = mapOf(
    "ic_search_20" to R.drawable.ic_search_20dp,
    "ic_filter_24" to R.drawable.ic_filter_24dp
)