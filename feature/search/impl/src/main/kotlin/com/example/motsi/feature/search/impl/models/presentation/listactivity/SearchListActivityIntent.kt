package com.example.motsi.feature.search.impl.models.presentation.listactivity


internal sealed class SearchListActivityIntent {
    //    data class EnterName(val name: String) : SearchIntent()
    data object ClickActivity : SearchListActivityIntent()
}