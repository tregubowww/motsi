package com.example.motsi.core.wizard.impl.models.presentation.wizardcoordinator

import com.example.motsi.core.wizard.impl.models.domain.WizardScreenModel


internal sealed class WizardCoordinatorIntent {
    data class OnAction(val action: WizardScreenModel.Action): WizardCoordinatorIntent()
}