package com.example.motsi.core.wizard.api

import com.example.motsi.core.navigation.models.Destination
import kotlinx.serialization.Serializable

@Serializable
data class WizardGraph(val url: String): Destination
