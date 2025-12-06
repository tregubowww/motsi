package com.example.motsi.feature.userprofile.impl.models.presentation

internal sealed interface UserProfileScreenEffect {
    data object NavigateToSubscriptionsScreen : UserProfileScreenEffect

    data object NavigateToSubscribersScreen : UserProfileScreenEffect

    data object NavigateToEditUserProfileScreen : UserProfileScreenEffect

    data class SendUserProfile(val userUrl: String) : UserProfileScreenEffect

    data object NavigateToSportTypeLevelInfoScreen : UserProfileScreenEffect
}