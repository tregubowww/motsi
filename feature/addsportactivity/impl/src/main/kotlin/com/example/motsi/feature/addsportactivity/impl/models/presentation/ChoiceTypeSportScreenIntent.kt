package com.example.motsi.feature.addsportactivity.impl.models.presentation

internal sealed class ChoiceTypeSportScreenIntent {
    data class ClickTypeSportField(
        val name: String,
    ) : ChoiceTypeSportScreenIntent()

    data class FilerListTypeSport(
        val text: String,
    ) : ChoiceTypeSportScreenIntent()
}