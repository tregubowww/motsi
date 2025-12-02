package com.example.motsi.core.wizard.impl.presentation.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.motsi.core.ui.designsystem.fields.BaseIconTextField
import com.example.motsi.core.ui.theming.AppResources
import com.example.motsi.core.wizard.impl.models.domain.WizardScreenModel
import com.example.motsi.core.wizard.impl.presentation.consts.DIVIDER_FLAG_KEY
import com.example.motsi.core.wizard.impl.presentation.consts.FIELD_ACTION_KEY
import com.example.motsi.core.wizard.impl.presentation.consts.ICON_KEY
import com.example.motsi.core.wizard.impl.presentation.consts.SUBTITLE_KEY
import com.example.motsi.core.wizard.impl.presentation.consts.TITLE_KEY


@Composable
internal fun IconTextWidget(
    widgetModel: WizardScreenModel.Widget,
    onAction: (action: WizardScreenModel.Action) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseIconTextField(
        modifier = modifier,
        onFieldClick = {
            widgetModel.actions?.get(FIELD_ACTION_KEY)?.let { onAction.invoke(it) }
        },
        icon = widgetModel.properties?.get(ICON_KEY)?.let { AppResources.iconRes(it) },
        title = widgetModel.properties?.get(TITLE_KEY).orEmpty(),
        subtitle = widgetModel.properties?.get(SUBTITLE_KEY).orEmpty(),
        isDividerVisible = widgetModel.properties?.get(DIVIDER_FLAG_KEY)?.toBoolean() ?: false
    )
}
