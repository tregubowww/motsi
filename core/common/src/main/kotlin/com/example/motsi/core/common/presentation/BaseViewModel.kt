package com.example.motsi.core.common.presentation

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T> : ViewModel(), IntentDispatcher<T> {


    protected abstract fun onRelease()

    override fun onCleared() {
        super.onCleared()
        onRelease()
    }
}

interface IntentDispatcher<I> {
    fun dispatch(intent: I)
}