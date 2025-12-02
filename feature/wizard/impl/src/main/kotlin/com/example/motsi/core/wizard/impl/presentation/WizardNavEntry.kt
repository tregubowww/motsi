package com.example.motsi.core.wizard.impl.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.motsi.core.di.holder.getFeatureApi
import com.example.motsi.core.navigation.presentation.FeatureNavEntry
import com.example.motsi.core.navigation.presentation.featureEntry
import com.example.motsi.core.wizard.api.WizardGraph
import com.example.motsi.core.wizard.impl.di.WizardHolder
import com.example.motsi.core.wizard.impl.di.WizardInternalApi
import com.example.motsi.core.wizard.impl.models.presentation.WizardDestination
import javax.inject.Inject

class WizardNavEntry @Inject constructor() : FeatureNavEntry {

    override fun NavGraphBuilder.register(
        bottomNavBar: @Composable () -> Unit
    ) {
        featureEntry<WizardInternalApi>(WizardHolder) {
            navigation<WizardGraph>(startDestination = WizardDestination) {
                val api = getFeatureApi<WizardInternalApi>()
                val factory = api.viewModelFactory()

                composable<WizardDestination> {
                    val viewModel: WizardScreenViewModel =
                        viewModel<WizardScreenViewModel>(factory = factory)

                    WizardScreen(
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}



