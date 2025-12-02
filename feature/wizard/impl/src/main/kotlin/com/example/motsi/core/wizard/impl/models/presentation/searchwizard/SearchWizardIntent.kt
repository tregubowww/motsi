package com.example.motsi.core.wizard.impl.models.presentation.searchwizard

import com.example.motsi.core.wizard.impl.models.domain.WizardScreenModel

internal sealed class SearchWizardIntent {
    data class ChangeSearchQuery(
        val query: String,
    ) : SearchWizardIntent()

    data class ClickTip(
        val item: WizardScreenModel.Widget.Item,
    ) : SearchWizardIntent()
}