package com.example.motsi.api

import com.example.motsi.core.navigation.models.Destination
import kotlinx.serialization.Serializable

@Serializable
data class ActivityDetailsGraph(val id: String): Destination

