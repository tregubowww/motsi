package com.example.motsi.core.wizard.impl.presentation.widgets.searchwizardwidget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.utils.assistedViewModel
import com.example.motsi.core.di.holder.getFeatureApi
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.SearchField
import com.example.motsi.core.ui.designsystem.chip.TipChip
import com.example.motsi.core.ui.utils.CollectEffect
import com.example.motsi.core.wizard.impl.di.WizardInternalApi
import com.example.motsi.core.wizard.impl.models.domain.WizardScreenModel
import com.example.motsi.core.wizard.impl.models.presentation.searchwizard.SearchWizardEffect
import com.example.motsi.core.wizard.impl.models.presentation.searchwizard.SearchWizardIntent
import com.example.motsi.core.wizard.impl.presentation.consts.FIELD_ACTION_KEY


@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun SearchWizardWidget(
    widgetModel: WizardScreenModel.Widget,
    onAction: (action: WizardScreenModel.Action) -> Unit,
    modifier: Modifier = Modifier,
) {
    val api = getFeatureApi<WizardInternalApi>()
    val assistedFactory = api.searchWizardWidgetViewModelFactory()
    val viewModel = assistedViewModel(
        vmClass = SearchWizardWidgetViewModel::class.java,
        assistedFactory = { arg -> assistedFactory.create(arg) },
        args = widgetModel,
        key = widgetModel.id
    )
    val itemListState by viewModel.itemListState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is SearchWizardEffect.OnClickItem -> {
                effect.item.actions?.get(FIELD_ACTION_KEY)?.let { onAction.invoke(it) }
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        SearchField(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .height(45.dp),
            query = "",
            onTextChange = { text ->
                viewModel.dispatch(
                    SearchWizardIntent.ChangeSearchQuery(text)
                )
            },
            hint = widgetModel.properties?.get(PROPERTIES_HINT_SEARCH_FIELD_KEY).orEmpty(),
            onKeyboardSearchButtonClick = { text ->
                viewModel.dispatch(
                    SearchWizardIntent.ChangeSearchQuery(text)
                )
            },
            isNeedToFocused = false
        )


        when (val state = itemListState) {
            is LoadingState.Loading -> {
                //            Loading()
            }

            is LoadingState.Success -> {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal =  16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.data.forEach { item ->

                        TipChip(
                            text = item.title,
                            onClick = {
                                viewModel.dispatch(SearchWizardIntent.ClickTip(item))
                            }
                        )
                    }
                }
            }

            is LoadingState.Error -> {
//            Error()
            }

            else -> Unit
        }
    }
}

const val PROPERTIES_HINT_SEARCH_FIELD_KEY = "PROPERTIES_HINT_SEARCH_FIELD_KEY"
