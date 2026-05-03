package com.example.motsi.core.wizard.impl.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel
import com.example.motsi.core.wizard.impl.presentation.widgets.searchwizardwidget.SearchWizardWidget
import com.example.motsi.core.wizard.impl.presentation.widgets.ButtonWizardWidget
import com.example.motsi.core.wizard.impl.presentation.widgets.IconTextWizardWidget

@Composable
internal fun WidgetRenderer(
    widgetModel: WizardCoordinatorModel.Widget,
    onAction: (action: WizardCoordinatorModel.Action) -> Unit,
) {
    when (widgetModel.type) {
        "IconTextWidget" -> IconTextWizardWidget(widgetModel, onAction)
        "SearchWizardWidget" -> SearchWizardWidget(widgetModel = widgetModel, onAction)
        "ButtonWizardWidget" -> ButtonWizardWidget(widgetModel = widgetModel, onAction)
        else -> Log.e("WidgetRenderer", "Unknown widget: ${widgetModel.type}")
    }
}
