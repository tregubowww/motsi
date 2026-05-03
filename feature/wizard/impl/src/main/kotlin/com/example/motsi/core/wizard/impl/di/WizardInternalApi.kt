package com.example.motsi.core.wizard.impl.di

import androidx.lifecycle.ViewModelProvider
import com.example.motsi.core.wizard.api.di.WizardApi
import com.example.motsi.core.wizard.impl.presentation.widgets.screens.wizardmapscreen.WizardMapScreenViewModel
import com.example.motsi.core.wizard.impl.presentation.widgets.searchwizardwidget.SearchWizardWidgetViewModel


internal interface WizardInternalApi : WizardApi {
    fun viewModelFactory(): ViewModelProvider.Factory
    fun searchWizardWidgetViewModelFactory(): SearchWizardWidgetViewModel.Factory
    fun mapScreenViewModelFactory(): WizardMapScreenViewModel.Factory
}