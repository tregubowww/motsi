package com.example.motsi.core.wizard.impl.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import com.example.motsi.core.wizard.impl.presentation.widgets.searchwizardwidget.SearchWizardWidget
import com.example.motsi.core.wizard.impl.models.domain.WizardScreenModel
import com.example.motsi.core.wizard.impl.presentation.widgets.ButtonWizardWidget
import com.example.motsi.core.wizard.impl.presentation.widgets.IconTextWidget

@Composable
internal fun WidgetRenderer(
    widgetModel: WizardScreenModel.Widget,
    onAction: (action: WizardScreenModel.Action) -> Unit,
) {
    when (widgetModel.type) {
        "IconTextWidget" -> IconTextWidget(widgetModel, onAction)
        "SearchWizardWidget" -> SearchWizardWidget(widgetModel = widgetModel, onAction)
        "ButtonWizardWidget" -> ButtonWizardWidget(widgetModel = widgetModel, onAction)
        else -> Log.e("WidgetRenderer", "Unknown widget: ${widgetModel.type}")
    }
}
