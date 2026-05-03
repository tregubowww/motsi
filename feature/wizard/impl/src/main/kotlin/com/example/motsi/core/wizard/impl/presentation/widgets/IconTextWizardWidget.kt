package com.example.motsi.core.wizard.impl.presentation.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.motsi.core.ui.designsystem.fields.BaseIconTextField
import com.example.motsi.core.ui.theming.AppResources
import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel
import com.example.motsi.core.wizard.impl.presentation.consts.WIZARD_DIVIDER_FLAG_KEY
import com.example.motsi.core.wizard.impl.presentation.consts.WIZARD_FIELD_ACTION_KEY
import com.example.motsi.core.wizard.impl.presentation.consts.WIZARD_ICON_KEY
import com.example.motsi.core.wizard.impl.presentation.consts.WIZARD_SUBTITLE_KEY
import com.example.motsi.core.wizard.impl.presentation.consts.WIZARD_TITLE_KEY


@Composable
internal fun IconTextWizardWidget(
    widgetModel: WizardCoordinatorModel.Widget,
    onAction: (action: WizardCoordinatorModel.Action) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseIconTextField(
        modifier = modifier,
        onFieldClick = {
            widgetModel.actions?.get(WIZARD_FIELD_ACTION_KEY)?.let { onAction.invoke(it) }
        },
        icon = widgetModel.properties?.get(WIZARD_ICON_KEY)?.let { AppResources.iconRes(it) },
        title = widgetModel.properties?.get(WIZARD_TITLE_KEY).orEmpty(),
        subtitle = widgetModel.properties?.get(WIZARD_SUBTITLE_KEY).orEmpty(),
        isDividerVisible = widgetModel.properties?.get(WIZARD_DIVIDER_FLAG_KEY)?.toBoolean() ?: false
    )
}
