package com.example.motsi.core.wizard.impl.models.presentation.searchwizard

import com.example.motsi.core.wizard.impl.models.domain.WizardScreenModel

internal sealed interface SearchWizardEffect {
    data class OnClickItem(val item: WizardScreenModel.Widget.Item) : SearchWizardEffect
}