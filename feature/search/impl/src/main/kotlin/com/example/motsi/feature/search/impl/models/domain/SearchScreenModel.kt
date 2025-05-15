package com.example.motsi.feature.search.impl.models.domain

import com.example.motsi.core.ui.designsystem.appbar.searchappbar.AppBarAction
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

internal data class SearchScreenModel(
    val appbar: AppBar = AppBar(),
) {
    data class AppBar(
        val navigationAction: AppBarAction? = null,
        val titleSearchField: String = "",
        val actions: ImmutableSet<AppBarAction> = persistentSetOf()
    )
}