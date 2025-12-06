package com.example.motsi.impl.models.presentation

internal sealed class SportActivityDetailsScreenEffect {
    data object SendSportActivity : SportActivityDetailsScreenEffect()
    data object ShowInfoAboutPrivateStatus : SportActivityDetailsScreenEffect()
    data object ShowInfoLevelSportActivity : SportActivityDetailsScreenEffect()
    data object AddSportActivity : SportActivityDetailsScreenEffect()
    data object OpenChatSportActivity : SportActivityDetailsScreenEffect()
}