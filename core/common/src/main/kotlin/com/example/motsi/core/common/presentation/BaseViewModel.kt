package com.example.motsi.core.common.presentation

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(){

    private var isInitialized = false

    fun initViewModel() {
        if (isInitialized) return
        isInitialized = true
        onInit()
    }

    protected abstract fun onInit()

    protected abstract fun onRelease()

    override fun onCleared() {
        super.onCleared()
        onRelease()
    }
}