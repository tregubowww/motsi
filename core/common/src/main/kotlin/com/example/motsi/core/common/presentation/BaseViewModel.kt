package com.example.motsi.core.common.presentation

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(){


    protected abstract fun onRelease()

    override fun onCleared() {
        super.onCleared()
        onRelease()
    }
}