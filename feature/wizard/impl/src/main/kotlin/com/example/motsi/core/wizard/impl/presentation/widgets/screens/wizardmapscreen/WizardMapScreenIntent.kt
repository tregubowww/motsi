package com.example.motsi.core.wizard.impl.presentation.widgets.screens.wizardmapscreen


sealed class WizardMapScreenIntent {
    data object OnLocationClick : WizardMapScreenIntent()

    data class CameraMoved(
        val latitude: Double,
        val longitude: Double,
        val zoom: Double,
        val rotation: Float
    ) :
        WizardMapScreenIntent()

    data object OnShowUserGeoposition : WizardMapScreenIntent()
}

