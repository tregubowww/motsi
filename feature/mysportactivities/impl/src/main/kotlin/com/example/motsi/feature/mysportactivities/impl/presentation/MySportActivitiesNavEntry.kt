package com.example.motsi.feature.mysportactivities.impl.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.motsi.core.di.holder.getFeatureApi
import com.example.motsi.core.navigation.presentation.FeatureNavEntry
import com.example.motsi.core.navigation.presentation.featureEntry
import com.example.motsi.feature.mysportactivities.api.MySportActivitiesGraph
import com.example.motsi.feature.mysportactivities.impl.di.MySportActivitiesHolder
import com.example.motsi.feature.mysportactivities.impl.di.MySportActivitiesInternalApi
import com.example.motsi.feature.mysportactivities.impl.models.presentation.MySportActivitiesDestination
import javax.inject.Inject

class MySportActivitiesNavEntry @Inject constructor() : FeatureNavEntry {

    override fun NavGraphBuilder.register(
        bottomNavBar: @Composable () -> Unit
    ) {
        featureEntry<MySportActivitiesInternalApi>(MySportActivitiesHolder) {
            navigation<MySportActivitiesGraph>(startDestination = MySportActivitiesDestination) {
                val api = getFeatureApi<MySportActivitiesInternalApi>()
                val factory = api.viewModelFactory()

                composable<MySportActivitiesDestination> {
                    val viewModel: MySportActivitiesViewModel =
                        viewModel<MySportActivitiesViewModel>(factory = factory, key = "WizardScreenViewModel")

                    MySportActivitiesScreen(
                        viewModel = viewModel,
                        bottomNavBar =bottomNavBar
                    )
                }
            }
        }
    }
}



