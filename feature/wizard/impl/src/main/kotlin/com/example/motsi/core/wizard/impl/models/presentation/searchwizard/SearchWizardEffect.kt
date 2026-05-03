package com.example.motsi.core.wizard.impl.models.presentation.searchwizard

import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel

internal sealed interface SearchWizardEffect {
    data class OnClickItem(val item: WizardCoordinatorModel.Widget.Item) : SearchWizardEffect
}