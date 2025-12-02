package com.example.motsi.core.wizard.impl.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.core.wizard.impl.models.domain.WizardScreenModel
import kotlinx.collections.immutable.ImmutableList


internal interface WizardInteractor {

    suspend fun getNextScreen(
        properties: Map<String, String>,
        step: Int
    ): ResultWrapper<WizardScreenModel, MotsiError>

    suspend fun getItems(
        query: String,
        urlGetSearchItems: String
    ): ResultWrapper<ImmutableList<WizardScreenModel.Widget.Item>, MotsiError>
}