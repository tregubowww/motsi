package com.example.motsi.presentation

import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.utils.handleState
import com.example.motsi.domain.MainInteractor
import com.example.motsi.models.domain.MainModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


internal class MainViewModel @Inject constructor(
    private val interactor: MainInteractor,
) : BaseViewModel() {

    val mainModel: StateFlow<MainModel> get() = _mainModel.asStateFlow()
    private val _mainModel =
        MutableStateFlow(MainModel())

    fun initViewModel() {
        viewModelScope.launch {
            launch {
                 (interactor.getMainModel().handleState() as? LoadingState.Success)?.let { _mainModel.value = it.data }
            }
        }
    }

    override fun onRelease() {
//        nothing
    }
}