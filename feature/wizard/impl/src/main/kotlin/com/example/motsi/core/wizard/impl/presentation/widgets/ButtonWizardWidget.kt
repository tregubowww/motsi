package com.example.motsi.core.wizard.impl.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.designsystem.buttons.BaseButton
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.core.wizard.impl.models.domain.WizardScreenModel
import com.example.motsi.core.wizard.impl.presentation.consts.FIELD_ACTION_KEY
import com.example.motsi.core.wizard.impl.presentation.consts.TITLE_KEY

@Composable
internal fun ButtonWizardWidget(
    widgetModel: WizardScreenModel.Widget,
    onAction: (action: WizardScreenModel.Action) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseButton(
        modifier = modifier.background(Tokens.Background.getColor()).padding(16.dp),
        text =  widgetModel.properties?.get(TITLE_KEY).orEmpty(),
        color = Tokens.BackgroundBrand,
        onClick = {
            widgetModel.actions?.get(FIELD_ACTION_KEY)?.let { onAction.invoke(it) }
        }
    )
}

