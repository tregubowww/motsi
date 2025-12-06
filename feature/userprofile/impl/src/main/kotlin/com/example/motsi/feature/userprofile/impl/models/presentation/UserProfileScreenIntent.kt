package com.example.motsi.feature.userprofile.impl.models.presentation

internal sealed class UserProfileScreenIntent {
    data object ClickOnSubscriptionsPickList : UserProfileScreenIntent()

    data object ClickOnSubscribersPickList : UserProfileScreenIntent()

    data object ClickOnEditUserProfileScreen : UserProfileScreenIntent()

    data class ClickOnSendUserProfile(val userUrl: String) : UserProfileScreenIntent()

    data object ClickOnLevel : UserProfileScreenIntent()
}