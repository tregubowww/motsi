package com.example.motsi.core.wizard.impl.presentation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.navigation.presentation.compose.LocalAppNavController
import com.example.motsi.core.ui.utils.CollectEffect
import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel
import com.example.motsi.core.wizard.impl.models.presentation.WizardStepDestination
import com.example.motsi.core.wizard.impl.models.presentation.wizardcoordinator.WizardCoordinatorIntent
import com.example.motsi.core.wizard.impl.presentation.widgets.screens.WizardBaseScreen
import com.example.motsi.core.wizard.impl.presentation.widgets.screens.wizardmapscreen.WizardMapScreen

@SuppressLint("RestrictedApi")
@Composable
internal fun WizardCoordinator(
    step: Int,
    viewModel: WizardCoordinatorViewModel
) {
    val navController = LocalAppNavController.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(step) {
        viewModel.loadStep(
            step = step,
            properties = emptyMap()
        )
    }

    CollectEffect(viewModel.effect) { action ->
        when (action) {
            is WizardCoordinatorModel.Action.NextScreen -> {
                navController.navigate(
                    WizardStepDestination(step + 1)
                )
            }

            is WizardCoordinatorModel.Action.PreviewScreen -> {
                navController.popBackStack()
            }

            is WizardCoordinatorModel.Action.ExitWizardFlow -> {
                val route = navController.currentBackStackEntry?.destination?.parent?.route
                route?.let { navController . popBackStack (it, inclusive = true) }
            }

            is WizardCoordinatorModel.Action.Deeplink -> {
                navController.navigate(action.uri)
            }

            else -> Unit
        }
    }

    when (val s = state) {
        is LoadingState.Loading -> {
            // Loading()
        }

        is LoadingState.Success -> {
            when(s.data.screen){
                is WizardCoordinatorModel.Screen.BaseScreen -> {
                    WizardBaseScreen(
                        model = (s.data.screen as WizardCoordinatorModel.Screen.BaseScreen).model,
                        onAction = {
                            viewModel.dispatch(
                                WizardCoordinatorIntent.OnAction(it)
                            )
                        }
                    )
                }
                is WizardCoordinatorModel.Screen.MapScreen -> {
                    WizardMapScreen(
                        model = (s.data.screen as WizardCoordinatorModel.Screen.MapScreen).model,
                        onAction = {
                            viewModel.dispatch(
                                WizardCoordinatorIntent.OnAction(it)
                            )
                        }
                    )
                }
            }
        }

        is LoadingState.Error -> {
            // Error()
        }

        else -> Unit
    }
}



