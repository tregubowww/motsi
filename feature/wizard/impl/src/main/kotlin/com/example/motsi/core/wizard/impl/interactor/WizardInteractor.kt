package com.example.motsi.core.wizard.impl.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.core.wizard.impl.models.domain.WizardCoordinatorModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList


internal interface WizardInteractor {

    suspend fun getNextScreen(
        properties: Map<String, String>,
        step: Int
    ): ResultWrapper<WizardCoordinatorModel, MotsiError>

    suspend fun getItems(
        query: String,
        urlGetSearchItems: String
    ): ResultWrapper<ImmutableList<WizardCoordinatorModel.Widget.Item>, MotsiError>

    suspend fun getMarkersForMapScreen(urlGetMarkers: String): ResultWrapper.Success<PersistentList<WizardCoordinatorModel.Widget.Item>>
}