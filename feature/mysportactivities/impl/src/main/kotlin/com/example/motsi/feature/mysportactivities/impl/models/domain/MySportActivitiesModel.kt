package com.example.motsi.feature.mysportactivities.impl.models.domain

import kotlinx.collections.immutable.ImmutableList

internal data class MySportActivitiesModel(
    val appBar: AppBar,
    val pageList: ImmutableList<Page>,
    val addSportActivityButton: AddSportActivityButton,
){
    data class AppBar(
        val title : String
    )

    data class Page(
        val title : String,
        val items: ImmutableList<Item>
    ){
        data class Item(
        val id: String,
        val title: String,
        val subtitle: String,
        val descriptionActivityIcon: String,
        val description: String,
        val participantList: List<Participant>,
        val logoIcon: String,
        val logoColor: String,
        val privateStatus: PrivateStatus,
        val isLiked: Boolean,
        val isAdd: Boolean,
        ) {
            enum class PrivateStatus {
                OPEN,
                PRIVATE
            }

            data class Participant(
                val urlUserPic: String
            )

        }
    }

    data class AddSportActivityButton(
        val bottomBarButtonTitle: String,
        val urlStartWizardAddSportActivity: String
    )
}

