package com.example.motsi.feature.mysportactivities.impl.models.domain

import kotlinx.collections.immutable.ImmutableList

internal data class MySportActivitiesModel(
    val appBar: AppBar,
    val pageList: ImmutableList<Page>,
    val addSportActivity: AddSportActivity,
){
    data class AppBar(
        val title : String
    )

    data class Page(
        val title : String
    )

    data class AddSportActivity(
        val bottomBarButtonTitle: String,
        val urlStartWizardAddSportActivity: String
    )
}

