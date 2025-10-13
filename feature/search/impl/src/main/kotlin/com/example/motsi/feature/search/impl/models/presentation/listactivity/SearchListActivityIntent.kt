package com.example.motsi.feature.search.impl.models.presentation.listactivity

import androidx.navigation.NavController
import com.example.motsi.feature.search.impl.models.presentation.SearchDestination


internal sealed class SearchListActivityIntent {
    //    data class EnterName(val name: String) : SearchIntent()
    data class ClickSportActivity(
        val navController: NavController,
        val activityId: String
    ) : SearchListActivityIntent()

    data class AddFilter(val filterData: SearchDestination.SearchFilterData) :
        SearchListActivityIntent()
}
