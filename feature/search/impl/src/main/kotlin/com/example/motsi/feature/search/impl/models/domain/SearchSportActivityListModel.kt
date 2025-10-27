package com.example.motsi.feature.search.impl.models.domain

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SearchSportActivityListModel(
    val searchQuery: String?,
    val searchHint: String?,
    val cityLocation: SportActivity.CityLocation,
    val sportActivityList: ImmutableList<SportActivity> = persistentListOf(),
    val historyTipList: ImmutableList<SearchTip> = persistentListOf(),

){
    data class SportActivity(
        val id: String,
        val typeSport: String,
        val descriptionActivity: String,
        val dateText: String,
        val locationText: String,
        val participantList: List<Participant>,
        val iconTypeSport: String,
        val colorTypeSport: String,
        val privateStatus: PrivateStatus,
        val mapData: MapData
    ) {
        enum class PrivateStatus{
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
        data class MapData(
            val locationPoint: Pair<Double, Double>,
            val zoom: Float = 0f,
            val azimuth: Float = 0f,
            val tilt: Float = 0f,
            val iconMark: String
        )
    }

}