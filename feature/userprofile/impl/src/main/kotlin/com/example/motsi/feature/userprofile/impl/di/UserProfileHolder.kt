package com.example.motsi.feature.userprofile.impl.di

import com.example.motsi.core.di.holder.InitHolder
import com.example.motsi.core.network.di.NetworkHolder
import com.example.motsi.core.wrappers.di.WrappersCoreHolder

internal object UserProfileHolder : InitHolder<UserProfileInternalApi>() {
    override fun create(): UserProfileInternalApi =
        DaggerUserProfileComponent.factory().create(NetworkHolder.getApi(), WrappersCoreHolder.getApi())
}