package com.example.motsi.feature.search.impl.models.data

import kotlinx.serialization.SerialName

data class SearchListDataModel(
    @SerialName("titleAppbar")
    val recommendationList: List<Activity>
){
    data class Activity(
        @SerialName("id")
        val id: String
    )
}
