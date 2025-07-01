package com.example.motsi.feature.search.impl.presentation

import androidx.navigation.NavHostController
import com.example.motsi.api.ActivityDetailsGraph
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
}