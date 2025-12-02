package com.example.motsi.core.wizard.impl.models.domain

import com.example.motsi.core.common.models.domain.SnackbarType
import com.example.motsi.core.common.presentation.validator.ValidatorType
import kotlinx.collections.immutable.ImmutableList

data class WizardScreenModel(
    val currentStep: Int,
    val appBar: AppBar?,
    val bottomBar: BottomBar?,
    val listWidget: ImmutableList<Widget>
) {

    data class AppBar(
        val iconNavigation: String?,
        val title: String,
        val actions: Map<String, Action>,
    )

    data class BottomBar(
        val listWidget: ImmutableList<Widget>
    )

    data class Widget(
        val id: String,
        val type: String,
        val validators: ImmutableList<ValidatorType>?,
        val properties: Map<String, String>?,
        val actions: Map<String, Action>?,
        val items: ImmutableList<Item>?,
        ) {

        data class Item(
            val id: String,
            val title: String,
            val subtitle: String?,
            val description: String?,
            val icon: String?,
            val actions: Map<String, Action>?,
            val validators:ImmutableList<ValidatorType>?,
        )
    }

    sealed interface Action {
        data class Deeplink(val uri: String) : Action
        data object PreviewScreen: Action
        data class NextScreen(val properties: Map<String, String>) : Action
        data class ExitWizardFlow(val properties: Map<String, String>) : Action
        data class ShowSnackBar(
            val message: String,
            val type: SnackbarType,
            val onDismissed: Action,
            val onActionPerformed: Action
        ) : Action
    }
}
