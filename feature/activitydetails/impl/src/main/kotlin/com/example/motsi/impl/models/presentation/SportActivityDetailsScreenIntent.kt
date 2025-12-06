package com.example.motsi.impl.models.presentation

internal sealed class SportActivityDetailsScreenIntent {
    data object ClickOnFavoritesStatus : SportActivityDetailsScreenIntent()
    data object ClickOnIconSendSportActivity : SportActivityDetailsScreenIntent()
    data object ClickOnIconPrivateStatus : SportActivityDetailsScreenIntent()
    data object ClickOnIconLevelSportActivity : SportActivityDetailsScreenIntent()
    data object ClickOnAddSportActivity : SportActivityDetailsScreenIntent()
    data object ClickOnOpenChatSportActivity : SportActivityDetailsScreenIntent()
}