package com.example.motsi.impl.di

import com.example.motsi.api.di.ActivityDetailsApi
import com.example.motsi.impl.presentation.ActivityDetailsViewModel


internal interface ActivityDetailsInnerApi : ActivityDetailsApi{
    val viewModel: ActivityDetailsViewModel
}
