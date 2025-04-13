package com.example.motsi.feature.search.impl.di

import com.example.motsi.feature.search.api.di.SearchApi
import com.example.motsi.feature.search.impl.presentation.SearchClickHandler
import com.example.motsi.feature.search.impl.presentation.SearchViewModel


internal interface SearchInnerApi
    : SearchApi
{
    val viewModel: SearchViewModel
    val clickHandler: SearchClickHandler.Factory
}