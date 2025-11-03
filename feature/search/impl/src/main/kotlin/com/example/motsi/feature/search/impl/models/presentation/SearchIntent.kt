package com.example.motsi.feature.search.impl.models.presentation

import com.example.motsi.feature.search.impl.models.presentation.listactivity.SearchListActivityIntent
import com.example.motsi.feature.search.impl.models.presentation.map.SearchMapIntent
import com.example.motsi.feature.search.impl.models.presentation.screen.SearchScreenIntent

/** Универсальный интент для SearchViewModel */
internal sealed class SearchIntent {

    /** Интенты экрана */
    data class Screen(val value: SearchScreenIntent) : SearchIntent()

    /** Интенты карты */
    data class Map(val value: SearchMapIntent) : SearchIntent()

    /** Интенты списка активностей */
    data class List(val value: SearchListActivityIntent) : SearchIntent()
}