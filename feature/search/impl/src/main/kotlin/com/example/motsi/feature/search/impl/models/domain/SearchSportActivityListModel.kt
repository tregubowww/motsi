package com.example.motsi.feature.search.impl.models.domain

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SearchSportActivityListModel(
    val searchQuery: String?,
    val searchHint: String?,
    val cityLocation: SportActivity.CityLocation,
    val sportActivityList: ImmutableList<SportActivity> = persistentListOf(),
    val mapData: MapData,
    val historyTipList: ImmutableList<SearchTip> = persistentListOf(),

    ) {
    data class SportActivity(
        val id: String,
        val title: String,
        val subtitle: String,
        val descriptionActivityIcon: String,
        val description: String,
        val participantList: List<Participant>,
        val logoIcon: String,
        val logoColor: String,
        val privateStatus: PrivateStatus,
        val isLiked: Boolean,
        val isAdd: Boolean,
        ) {
        enum class PrivateStatus {
            OPEN,
            PRIVATE
        }

        data class CityLocation(
            val cityPoint: Pair<Double, Double>,
            val cityZoom: Double
        )

        data class Participant(
            val urlUserPic: String
        )

    }

    data class MapData(
        val zoom: Float = 0f,
        val azimuth: Float = 0f,
        val tilt: Float = 0f,
        val markers: ImmutableList<Marker>,
    ) {
        data class Marker(
            val id: String,
            val locationPoint: Pair<Double, Double>,
            val description: String,
            val type: String
        )
    }
}