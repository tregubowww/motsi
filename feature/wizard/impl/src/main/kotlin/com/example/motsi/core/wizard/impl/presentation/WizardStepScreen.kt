package com.example.motsi.core.wizard.impl.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.navigation.presentation.compose.LocalAppNavController
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.AppBarAction
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.BaseAppBar
import com.example.motsi.core.ui.theming.AppResources
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.core.ui.utils.CollectEffect
import com.example.motsi.core.wizard.impl.models.domain.WizardScreenModel
import com.example.motsi.core.wizard.impl.models.presentation.WizardStepDestination
import com.example.motsi.core.wizard.impl.models.presentation.wizardcoordinator.WizardCoordinatorIntent
import com.example.motsi.core.wizard.impl.presentation.consts.NAVIGATION_ACTION_KEY

@SuppressLint("RestrictedApi")
@Composable
internal fun WizardStepScreen(
    step: Int,
    viewModel: WizardScreenViewModel
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
            is WizardScreenModel.Action.NextScreen -> {
                navController.navigate(
                    WizardStepDestination(step + 1)
                )
            }

            is WizardScreenModel.Action.PreviewScreen -> {
                navController.popBackStack()
            }

            is WizardScreenModel.Action.ExitWizardFlow -> {
                val route = navController.currentBackStackEntry?.destination?.parent?.route
                route?.let { navController . popBackStack (it, inclusive = true) }
            }

            is WizardScreenModel.Action.Deeplink -> {
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
            WizardContent(
                model = s.data,
                onAction = {
                    viewModel.dispatch(
                        WizardCoordinatorIntent.OnAction(it)
                    )
                }
            )
        }

        is LoadingState.Error -> {
            // Error()
        }

        else -> Unit
    }
}

@Composable
internal fun WizardContent(
    model: WizardScreenModel,
    onAction: (WizardScreenModel.Action) -> Unit
) {
    Scaffold(
        topBar = {
            model.appBar?.let { appBar ->
                BaseAppBar(
                    modifier = Modifier.background(
                        Tokens.Background.getColor()
                    ),
                    navigationItem = appBar.iconNavigation?.let {
                        AppBarAction(
                            iconRes = AppResources.iconRes(it),
                            iconTint = Tokens.IconPrimary.getColor(),
                            onClick = {
                                appBar.actions[NAVIGATION_ACTION_KEY]
                                    ?.let(onAction)
                            }
                        )
                    },
                    title = appBar.title
                )
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Tokens.Background.getColor())
                    .navigationBarsPadding()
            ) {
                model.bottomBar?.listWidget?.forEach {
                    WidgetRenderer(
                        widgetModel = it,
                        onAction = onAction
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Tokens.Background.getColor())
        ) {
            model.listWidget.forEach {
                WidgetRenderer(
                    widgetModel = it,
                    onAction = onAction
                )
            }
        }
    }
}


