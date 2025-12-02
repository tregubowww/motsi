package com.example.motsi.feature.mysportactivities.impl.models.presentation


internal sealed class MySportActivitiesIntent {
    data object AddSportActivity: MySportActivitiesIntent()
}