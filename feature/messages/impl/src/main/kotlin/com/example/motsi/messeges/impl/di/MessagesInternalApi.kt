package com.example.motsi.messeges.impl.di

import androidx.lifecycle.ViewModelProvider
import com.example.motsi.messages.api.di.MessagesApi

internal interface MessagesInternalApi : MessagesApi {
    fun viewModelFactory(): ViewModelProvider.Factory
}