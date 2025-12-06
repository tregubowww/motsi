package com.example.motsi.feature.userprofile.impl.models.domain

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class UserProfileScreenModel(
    val id: String,
    val sportTypeTitle: String,
    val sportActivityTitle: String,
    val subscribers: Followers,
    val subscriptions: Followers,
    val userProfilePicList: ImmutableList<Pic> = persistentListOf(),
    val sportTypeList: ImmutableList<SportType> = persistentListOf(),
    val userInformation: UserInformation,
){
    data class Followers(
        val title: String,
        val count: String,
        val picList: ImmutableList<Pic> = persistentListOf(),
    )

    data class Pic(
        val id: String,
        val urlPic: String
    )

    data class SportType(
        val id: String,
        val type: String,
        val count: String,
        val rating: String? = null,
        val urlPic: String
    )

    data class UserInformation(
        val url: String,
        val name: String,
        val phoneNumberTitle: String,
        val phoneNumberValue: String,
        val usernameTitle: String,
        val usernameValue: String,
        val birthdayTitle: String,
        val birthdayValue: String,
        val informationTitle: String,
        val informationValue: String,
    )
}