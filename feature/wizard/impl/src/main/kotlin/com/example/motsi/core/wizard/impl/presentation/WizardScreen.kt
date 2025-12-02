package com.example.motsi.core.wizard.impl.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.navigation.presentation.compose.LocalAppNavController
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.AppBarAction
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.BaseAppBar
import com.example.motsi.core.ui.designsystem.snackbar.CustomSnackbarHost
import com.example.motsi.core.ui.designsystem.snackbar.showMotsiSnackbar
import com.example.motsi.core.ui.models.DataSnackbar
import com.example.motsi.core.ui.theming.AppResources
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.core.ui.utils.CollectEffect
import com.example.motsi.core.wizard.impl.models.presentation.wizardcoordinator.WizardCoordinatorIntent
import com.example.motsi.core.wizard.impl.models.domain.WizardScreenModel
import com.example.motsi.core.wizard.impl.presentation.consts.NAVIGATION_ACTION_KEY
import kotlinx.coroutines.launch

@Composable
internal fun WizardScreen(viewModel: WizardScreenViewModel) {

    val navController = LocalAppNavController.current
    val screenState by viewModel.currentScreenState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is WizardScreenModel.Action.ExitWizardFlow -> {
                navController.popBackStack()
            }

            is WizardScreenModel.Action.Deeplink -> {
                navController.navigate(effect.uri)
            }

            is WizardScreenModel.Action.ShowSnackBar -> {
                coroutineScope.launch {
                    snackbarHostState.showMotsiSnackbar(
                        dataSnackbar = DataSnackbar(message = effect.message, type = effect.type),
                        onDismissed = {
                            viewModel.dispatch(WizardCoordinatorIntent.OnAction(effect.onDismissed))
                        },
                        onActionPerformed = {
                            viewModel.dispatch(WizardCoordinatorIntent.OnAction(effect.onActionPerformed))
                        }
                    )
                }
            }

            else -> Unit
        }
    }

    when (val state = screenState) {
        is LoadingState.Loading -> {
            //            Loading()
        }

        is LoadingState.Success -> {
            AnimatedContent(
                targetState = state.data,
                transitionSpec = {
                    fadeIn(tween(250)) togetherWith fadeOut(tween(250))
                },
                label = "wizard_fullscreen_transition"
            ) { data ->

                Scaffold(
                    modifier = Modifier,
                    topBar = {
                        data.appBar?.let { appBar ->
                            BaseAppBar(
                                modifier = Modifier.background(color = Tokens.Background.getColor()),
                                navigationItem = appBar.iconNavigation?.let {
                                    AppBarAction(
                                        iconRes = AppResources.iconRes(it),
                                        iconTint = Tokens.IconPrimary.getColor(),
                                        onClick = {
                                            data.appBar.actions[NAVIGATION_ACTION_KEY]?.let { action ->
                                                viewModel.dispatch(
                                                    WizardCoordinatorIntent.OnAction(
                                                        action
                                                    )
                                                )
                                            }
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
                            data.bottomBar?.listWidget?.forEach { widgetModel ->
                                WidgetRenderer(
                                    widgetModel = widgetModel,
                                    onAction = { action ->
                                        viewModel.dispatch(WizardCoordinatorIntent.OnAction(action))
                                    })
                            }
                        }
                    },
                    snackbarHost = {
                        CustomSnackbarHost(
                            hostState = snackbarHostState,
                        )
                    }
                ) { padding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .background(Tokens.Background.getColor())
                    ) {
                        data.listWidget.forEach { widgetModel ->
                            WidgetRenderer(
                                widgetModel = widgetModel,
                                onAction = { action ->
                                    viewModel.dispatch(WizardCoordinatorIntent.OnAction(action))
                                })
                        }
                    }
                }
            }
        }

        is LoadingState.Error -> {
//            Error()
        }

        else -> Unit
    }

}


