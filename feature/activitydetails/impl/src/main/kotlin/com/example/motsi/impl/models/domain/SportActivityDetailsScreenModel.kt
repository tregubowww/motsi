package com.example.motsi.impl.models.domain

import kotlinx.collections.immutable.ImmutableList

internal data class SportActivityDetailsScreenModel(
    val typeSport: String,
    val typeSportActivity: String,
    val typeSportActivityDescription: String,
    val dateText: String,
    val isFavorites: Boolean,
    val level: Level?,
    val participantList: ImmutableList<Participant>,
    val  privateStatus: PrivateStatus,
    val  buttons: Buttons

    ) {
    data class Participant(
        val urlUserPic: String
    )

    sealed class PrivateStatus{
        data object Open: PrivateStatus()
        data class Private(val title: String, val description: String, val icon: String,): PrivateStatus()
    }

    data class Buttons(
        val titleChat: String,
        val titleAddSportActivity: String,
    )
    data class Level(
        val text: String,
        val color: String
    )
}
