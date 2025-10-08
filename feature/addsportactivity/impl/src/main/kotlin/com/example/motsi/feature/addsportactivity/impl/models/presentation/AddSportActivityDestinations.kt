package com.example.motsi.feature.addsportactivity.impl.models.presentation

import kotlinx.serialization.Serializable

@Serializable
internal data class AddSportActivityDestination(
    val data: AddSportActivityData = AddSportActivityData(),
) {

    @Serializable
     data class AddSportActivityData(
        val typeSport: String? = null,
        val typeSportActivity: String? = null,
    )
}


@Serializable
internal data object ChoiceTypeSportDestination
