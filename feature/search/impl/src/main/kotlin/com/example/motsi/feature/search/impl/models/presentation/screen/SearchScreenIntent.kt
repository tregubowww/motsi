package com.example.motsi.feature.search.impl.models.presentation.screen


internal sealed class SearchScreenIntent {
    data object ClickSearchField : SearchScreenIntent()
}