package com.example.motsi.feature.search.impl.di

import androidx.lifecycle.ViewModelProvider
import com.example.motsi.feature.search.api.di.SearchApi
import com.example.motsi.feature.search.impl.presentation.SearchTipsViewModel


internal interface SearchInternalApi : SearchApi {
    fun viewModelFactory(): ViewModelProvider.Factory
    fun searchTipsViewModelFactory(): SearchTipsViewModel.Factory
}