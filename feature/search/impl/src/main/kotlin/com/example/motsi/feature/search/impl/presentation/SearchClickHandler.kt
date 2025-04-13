package com.example.motsi.feature.search.impl.presentation

import androidx.navigation.NavHostController
import com.example.motsi.api.ActivityDetailsGraph
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

internal class SearchClickHandler @AssistedInject constructor(@Assisted private val navController: NavHostController) {

    fun onActivityClick(){
        navController.navigate(
            ActivityDetailsGraph
                (id = "123"),

        )
    }

    @AssistedFactory
    interface Factory {
        fun create(navController: NavHostController): SearchClickHandler
    }
}