package com.example.motsi.feature.addsportactivity.impl.models.domain

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class ChoiceTypeSportModel(
    val title: String,
    val searchHint: String,
    val listTypeSport: ImmutableList<TypeSport> = persistentListOf(),
){
    data class TypeSport(
        val name: String,
        val sportGroup: String,
        val icon: String,
    )
}