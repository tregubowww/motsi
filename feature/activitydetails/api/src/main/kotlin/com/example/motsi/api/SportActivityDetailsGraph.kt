package com.example.motsi.api

import com.example.motsi.core.navigation.models.Destination
import kotlinx.serialization.Serializable

@Serializable
data class SportActivityDetailsGraph(val id: String): Destination

