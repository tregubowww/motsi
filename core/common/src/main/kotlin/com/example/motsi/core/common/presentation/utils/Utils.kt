package com.example.motsi.core.common.presentation.utils

inline fun <reified T : Enum<T>> String?.enumOrDefault(default: T): T =
    this?.let { enumValues<T>().firstOrNull { e -> e.name == it } } ?: default