package com.example.motsi.feature.search.impl.models.domain

internal data class SearchFilterModel(
    val mapFilter: MapFilter = MapFilter()
){
    data class MapFilter(
        val latitude: Double? = null,
        val longitude: Double? = null,
        val zoom: Double? = null,
        val rotation: Float? = null,
        val choiceMarker: String? = null,
    )
}