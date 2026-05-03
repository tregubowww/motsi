package com.example.motsi.core.wizard.impl.presentation.widgets.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.AppBarAction
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.BaseAppBar
import com.example.motsi.core.ui.theming.AppResources
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel
import com.example.motsi.core.wizard.impl.presentation.WidgetRenderer
import com.example.motsi.core.wizard.impl.presentation.consts.WIZARD_NAVIGATION_ACTION_KEY


@Composable
internal fun WizardBaseScreen(
    model: WizardCoordinatorModel.BaseScreenModel,
    onAction: (WizardCoordinatorModel.Action) -> Unit
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
                                appBar.actions[WIZARD_NAVIGATION_ACTION_KEY]
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