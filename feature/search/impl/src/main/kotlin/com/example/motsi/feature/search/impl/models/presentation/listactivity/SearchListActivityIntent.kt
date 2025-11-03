package com.example.motsi.feature.search.impl.models.presentation.listactivity

import com.example.motsi.feature.search.impl.models.presentation.SearchDestination


internal sealed class SearchListActivityIntent {
    data class AddFilter(val filterData: SearchDestination.SearchFilterData) :
        SearchListActivityIntent()

    data class ClickSportActivity(val activityId: String) : SearchListActivityIntent()
}
