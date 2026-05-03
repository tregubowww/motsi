package com.example.motsi.core.wizard.impl.models.presentation.wizardcoordinator

import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel


internal sealed class WizardCoordinatorIntent {
    data class OnAction(val action: WizardCoordinatorModel.Action): WizardCoordinatorIntent()
}