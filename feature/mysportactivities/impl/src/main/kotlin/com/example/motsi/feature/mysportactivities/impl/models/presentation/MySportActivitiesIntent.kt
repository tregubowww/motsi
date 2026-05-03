package com.example.motsi.feature.mysportactivities.impl.models.presentation


internal sealed class MySportActivitiesIntent {
    data object ClickAddSportActivityButton : MySportActivitiesIntent()
    data class ClickSportActivityItem(val id: String) : MySportActivitiesIntent()
}