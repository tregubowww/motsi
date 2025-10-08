package com.example.motsi.feature.addsportactivity.impl.presentation.compose

import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.feature.addsportactivity.impl.domain.interactor.AddSportActivityInteractor
import javax.inject.Inject

internal class AddSportActivityViewModel @Inject constructor(
    private val interactor: AddSportActivityInteractor,
) : BaseViewModel() {

    fun initViewModel() {

    }

    override fun onRelease() {
//        nothing
    }
}