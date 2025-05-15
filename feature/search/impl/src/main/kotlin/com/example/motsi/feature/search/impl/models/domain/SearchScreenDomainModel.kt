package com.example.motsi.feature.search.impl.models.domain

import com.example.motsi.core.ui.designsystem.appbar.searchappbar.AppBarAction

internal data class SearchScreenDomainModel(
    val appbar: AppBar,
){
    data class AppBar(
        val navigationAction: AppBarAction?,
        val titleSearchField: String,
        val actions: Set<AppBarAction>
        )
}