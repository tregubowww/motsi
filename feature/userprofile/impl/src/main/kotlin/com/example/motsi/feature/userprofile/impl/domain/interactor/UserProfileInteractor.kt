package com.example.motsi.feature.userprofile.impl.domain.interactor

import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.userprofile.impl.models.domain.UserProfileScreenModel

internal interface UserProfileInteractor {

    suspend fun getUserProfileScreen(): ResultWrapper<UserProfileScreenModel, MotsiError>
}