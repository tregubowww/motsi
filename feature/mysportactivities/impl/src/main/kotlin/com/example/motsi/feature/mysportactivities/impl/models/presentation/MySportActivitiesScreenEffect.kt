package com.example.motsi.feature.mysportactivities.impl.models.presentation

internal sealed interface MySportActivitiesScreenEffect {
    data class OpenAddSportActivityScreen(val url: String) : MySportActivitiesScreenEffect
    data class OpenSportActivityDetailsScreen(val id: String) : MySportActivitiesScreenEffect
}