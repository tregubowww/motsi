package com.example.motsi.feature.search.impl.presentation

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.motsi.core.di.holder.getFeatureApi
import com.example.motsi.core.navigation.presentation.SharedSplashScreenViewModel
import com.example.motsi.core.navigation.presentation.FeatureNavEntry
import com.example.motsi.core.navigation.presentation.MotsiNavType
import com.example.motsi.core.navigation.presentation.featureEntry
import com.example.motsi.feature.search.api.SearchGraph
import com.example.motsi.feature.search.impl.di.SearchHolder
import com.example.motsi.feature.search.impl.di.SearchInternalApi
import com.example.motsi.feature.search.impl.models.presentation.SearchDestination
import com.example.motsi.feature.search.impl.models.presentation.SearchTipsDestination
import com.example.motsi.feature.search.impl.presentation.compose.SearchScreen
import com.example.motsi.feature.search.impl.presentation.compose.SearchTipsScreen
import javax.inject.Inject
import kotlin.reflect.typeOf

class SearchNavEntry @Inject constructor() : FeatureNavEntry {

    override fun NavGraphBuilder.register(
        bottomNavBar: @Composable () -> Unit
    ) {
        featureEntry<SearchInternalApi>(SearchHolder) {
            navigation<SearchGraph>(startDestination = SearchDestination()) {
                val api = getFeatureApi<SearchInternalApi>()
                val factory = api.viewModelFactory()

//                костыль для сериализации SearchFilterData может быть в более новых версиях compose navigation  поправится и добавится возможность использовать разные типы
                val searchFilterDataNavType = MotsiNavType(SearchDestination.SearchFilterData.serializer())
                composable<SearchDestination>( typeMap = mapOf(typeOf<SearchDestination.SearchFilterData>() to searchFilterDataNavType)) { entry ->

                    val args = entry.toRoute<SearchDestination>()
                    val viewModel: SearchViewModel = viewModel(factory = factory)

                    val sharedSplashScreenViewModel: SharedSplashScreenViewModel = viewModel(
                        viewModelStoreOwner = LocalActivity.current as ComponentActivity,
                        factory = factory
                    )
                    SearchScreen(
                        viewModel = viewModel,
                        hideSplashScreen = { sharedSplashScreenViewModel.hideSplashScreen() },
                        bottomNavBar = bottomNavBar,
                        filterData = args.filterData
                    )
                }

                val searchTipListNavType = MotsiNavType(SearchTipsDestination.EntryData.serializer())
                composable<SearchTipsDestination>( typeMap = mapOf(typeOf<SearchTipsDestination.EntryData>() to searchTipListNavType)) { entry ->
                    val args = entry.toRoute<SearchTipsDestination>()
                    val viewModel: SearchTipsViewModel = viewModel(factory = factory)

                    SearchTipsScreen(
                        viewModel = viewModel,
                        bottomNavBar = bottomNavBar,
                        entryData = args.entryData,
                    )
                }
            }
        }
    }
}

