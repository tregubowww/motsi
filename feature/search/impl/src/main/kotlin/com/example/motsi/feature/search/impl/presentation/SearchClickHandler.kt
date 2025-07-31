package com.example.motsi.feature.search.impl.presentation

import android.annotation.SuppressLint
import androidx.navigation.NavHostController
import com.example.motsi.api.ActivityDetailsGraph
import com.example.motsi.feature.search.impl.models.presentation.SearchDestination
import com.example.motsi.feature.search.impl.models.presentation.SearchTipsDestination
import javax.inject.Inject

internal class SearchClickHandler @Inject constructor() {

    @Synchronized
    fun onActivityClick(navController: NavHostController) {

        navController.navigate(
            ActivityDetailsGraph
//            хардкод
                (id = (0..100).random().toString()),
        )
    }

    fun onSearchFieldClick(navController: NavHostController, searchQuery: String, appBarHint: String) {
        navController.navigate(
            SearchTipsDestination(searchQuery = searchQuery, appBarHint = appBarHint)
        ) {
            launchSingleTop = true
        }
    }

    @SuppressLint("RestrictedApi")
    fun onTipClick(navController: NavHostController, query: String) {
        navController.navigate(
            SearchDestination(searchQuery = query)
        ) {
            popUpTo(SearchDestination::class) { inclusive = true }
        }
    }


    fun onClickCancel(navController: NavHostController) {
        navController.popBackStack()
    }
}