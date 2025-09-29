package com.example.motsi.impl.models.presentation

internal sealed class SportActivityDetailsScreenIntent {
    data object ChangeFavoritesStatus : SportActivityDetailsScreenIntent()
    data object SendSportActivity : SportActivityDetailsScreenIntent()
    data object ShowInfoAboutPrivateStatus : SportActivityDetailsScreenIntent()
    data object ShowInfoLevelSportActivity : SportActivityDetailsScreenIntent()
    data object AddSportActivity : SportActivityDetailsScreenIntent()
    data object OpenChatSportActivity : SportActivityDetailsScreenIntent()
}