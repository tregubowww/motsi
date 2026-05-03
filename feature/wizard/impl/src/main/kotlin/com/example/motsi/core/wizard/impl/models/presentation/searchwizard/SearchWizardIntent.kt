package com.example.motsi.core.wizard.impl.models.presentation.searchwizard

import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel

internal sealed class SearchWizardIntent {
    data class ChangeSearchQuery(
        val query: String,
    ) : SearchWizardIntent()

    data class ClickTip(
        val item: WizardCoordinatorModel.Widget.Item,
    ) : SearchWizardIntent()
}