package com.example.motsi.feature.search.impl.presentation

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.motsi.core.di.holder.getFeatureApi
import com.example.motsi.core.navigation.presentation.SharedSplashScreenViewModel
import com.example.motsi.core.navigation.presentation.FeatureNavEntry
import com.example.motsi.core.navigation.presentation.featureEntry
import com.example.motsi.feature.search.api.SearchGraph
import com.example.motsi.feature.search.impl.di.SearchHolder
import com.example.motsi.feature.search.impl.di.SearchInternalApi
import com.example.motsi.feature.search.impl.models.presentation.SearchDestination
import com.example.motsi.feature.search.impl.presentation.compose.SearchScreen
import javax.inject.Inject

class SearchNavEntry @Inject constructor() : FeatureNavEntry {

    override fun NavGraphBuilder.register(
        bottomNavBar: @Composable () -> Unit
    ) {
        featureEntry<SearchInternalApi>(SearchHolder) {
            navigation<SearchGraph>(startDestination = SearchDestination) {
                val api = getFeatureApi<SearchInternalApi>()
                val factory = api.viewModelFactory()
                val clickHandler = api.clickHandler()

                composable<SearchDestination> {

                    val viewModel: SearchViewModel = viewModel(factory = factory)
                    val sharedSplashScreenViewModel: SharedSplashScreenViewModel = viewModel(
                        viewModelStoreOwner = LocalActivity.current as ComponentActivity,
                        factory = factory
                    )
                    SearchScreen(
                        viewModel = viewModel,
                        hideSplashScreen = { sharedSplashScreenViewModel.hideSplashScreen() },
                        clickHandler = clickHandler,
                        bottomNavBar = bottomNavBar
                    )
                }
            }
        }
    }
}