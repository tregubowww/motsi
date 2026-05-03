package com.example.motsi.core.wizard.impl.presentation.widgets.screens.wizardmapscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.motsi.core.common.presentation.utils.assistedViewModel
import com.example.motsi.core.di.holder.getFeatureApi
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.AppBarAction
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.SearchAppBar
import com.example.motsi.core.ui.designsystem.snackbar.CustomSnackbarHost
import com.example.motsi.core.ui.theming.AppResources
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.core.wizard.impl.di.WizardInternalApi
import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel
import com.example.motsi.core.wizard.impl.presentation.WidgetRenderer
import com.example.motsi.core.wizard.impl.presentation.consts.WIZARD_HINT_KEY
import com.example.motsi.core.wizard.impl.presentation.consts.WIZARD_NAVIGATION_ACTION_KEY
import com.example.motsi.core.wizard.impl.presentation.consts.WIZARD_SEARCH_QUERY_KEY
import com.example.motsi.core.ui.designsystem.mapwidget.MapWidget
import com.example.motsi.core.ui.designsystem.mapwidget.MapWidgetActions


@Composable
internal fun WizardMapScreen(
    model: WizardCoordinatorModel.MapScreenModel,
    onAction: (WizardCoordinatorModel.Action) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            CustomSnackbarHost(
                hostState = snackbarHostState,
            )
        },
        topBar = {
            model.appBar?.let { appBar ->
                SearchAppBar(
                    modifier = Modifier.background(Tokens.Background.getColor()),
                    onSearchFieldClick = {
//                        viewModel.dispatch(
//                                WizardMapScreenIntent.ClickSearchField(
//                                    query = query,
//                                    hint = hint,
//                                    historyTipList = historyTipList,
//                                )
//                        )
                        TODO("открыть экран поиска местоположения")

                    },
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
                    hint = appBar.properties?.get(WIZARD_HINT_KEY).orEmpty(),
                    textSearch = appBar.properties?.get(WIZARD_SEARCH_QUERY_KEY).orEmpty(),
                    onTextChange = {},
                    isEnabled = false
                )
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Tokens.Transparent.getColor())
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
        val api = getFeatureApi<WizardInternalApi>()
        val assistedFactory = api.mapScreenViewModelFactory()
        val viewModel = assistedViewModel(
            vmClass = WizardMapScreenViewModel::class.java,
            assistedFactory = { arg -> assistedFactory.create(arg) },
            args = model,
            key = model.idScreen
        )

        val mapState by viewModel.mapState.collectAsState()
        MapWidget(
            modifier = Modifier
                .background(Tokens.Background.getColor())
                .padding(top = padding.calculateTopPadding() + 12.dp),

            actions = MapWidgetActions(
                onCameraMoved = { lat, lon, zoom, rotation ->
                    viewModel.dispatch(
                        WizardMapScreenIntent.CameraMoved(
                            lat,
                            lon,
                            zoom,
                            rotation
                        )
                    )
                },
                onShowMobileLocation = {
                    viewModel.dispatch(WizardMapScreenIntent.OnShowUserGeoposition)
                },
                onGetMobileLocationClick = {
                    viewModel.dispatch(WizardMapScreenIntent.OnLocationClick)
                },
                onPointClick = {
                    TODO()
                }
            ),
            snackBarFlow = viewModel.snackBar,
            snackbarHostState = snackbarHostState,
            state = mapState,
        )
    }
}