package com.example.motsi.feature.search.impl.di

import androidx.lifecycle.ViewModelProvider
import com.example.motsi.feature.search.api.di.SearchApi


internal interface SearchInternalApi : SearchApi {
    fun viewModelFactory(): ViewModelProvider.Factory
}