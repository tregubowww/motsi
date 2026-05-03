package com.example.motsi.core.wizard.impl.presentation.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.designsystem.buttons.BaseButton
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel
import com.example.motsi.core.wizard.impl.presentation.consts.WIZARD_FIELD_ACTION_KEY
import com.example.motsi.core.wizard.impl.presentation.consts.WIZARD_TITLE_KEY

@Composable
internal fun ButtonWizardWidget(
    widgetModel: WizardCoordinatorModel.Widget,
    onAction: (action: WizardCoordinatorModel.Action) -> Unit,
    modifier: Modifier = Modifier,
) {


    BaseButton(
        modifier = modifier.padding(16.dp),
        text = widgetModel.properties?.get(WIZARD_TITLE_KEY).orEmpty(),
        color = Tokens.BackgroundBrand,
        onClick = {
            widgetModel.actions?.get(WIZARD_FIELD_ACTION_KEY)?.let { onAction.invoke(it) }
        }
    )
}

