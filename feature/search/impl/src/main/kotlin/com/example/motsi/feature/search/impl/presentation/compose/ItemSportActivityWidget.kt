package com.example.motsi.feature.search.impl.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.designsystem.userpics.UserPics
import com.example.motsi.core.ui.theming.AppResources
import com.example.motsi.core.ui.theming.Body3Primary
import com.example.motsi.core.ui.theming.Body3Secondary
import com.example.motsi.core.ui.theming.Title1Primary
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.feature.search.impl.models.domain.SearchSportActivityListModel
import com.example.motsi.feature.search.impl.models.domain.SearchSportActivityListModel.SportActivity.PrivateStatus

@Composable
internal fun ItemSportActivity(
    modifier: Modifier,
    sportActivityItem: SearchSportActivityListModel.SportActivity,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .height(240.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = Tokens.BackgroundSecondary.getColor()
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .background(
                color = Tokens.BackgroundSecondary.getColor(),
                shape = RoundedCornerShape(16.dp)
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()) {

            UserPics(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                iconSize = 50,
                urlPicsList = sportActivityItem.participantList.map { it.urlUserPic }
            )
            Column {
                Icon(
                    painter = painterResource(com.example.motsi.core.ui.R.drawable.ic_like_24_dp),
                    contentDescription = null,
                    tint = Tokens.IconPrimary.getColor(),
                    modifier = Modifier
                        .padding(12.dp)
                        .size(28.dp)
                        .clickable(role = Role.Button) {
                        }
                )
                Icon(
                    painter = painterResource(com.example.motsi.core.ui.R.drawable.ic_message_24dp),
                    contentDescription = null,
                    tint = Tokens.IconBrand1.getColor(),
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .size(30.dp)
                        .clickable {
                        }
                )
                Icon(
                    painter = painterResource(com.example.motsi.core.ui.R.drawable.ic_circle_plus_24dp),
                    contentDescription = null,
                    tint = Tokens.IconBrand2.getColor(),
                    modifier = Modifier
                        .padding(12.dp)
                        .size(30.dp)
                        .clickable {
                        }
                )
            }
        }
        Row(modifier = Modifier, verticalAlignment = Alignment.Bottom) {
            Column(modifier = Modifier.weight(1f)) {

                Title1Primary(
                    text = sportActivityItem.typeSport,
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    maxLines = 1
                )

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Body3Primary(
                        text = sportActivityItem.descriptionActivity,
                        Modifier.padding(start = 16.dp, end = 16.dp),
                        maxLines = 1
                    )

                    if (sportActivityItem.privateStatus == PrivateStatus.PRIVATE) {
                        Icon(
                            painter = painterResource(com.example.motsi.core.ui.R.drawable.ic_lock_24dp),
                            contentDescription = null,
                            tint = Tokens.IconPrimary.getColor(),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Body3Secondary(
                    text = sportActivityItem.dateText,
                    Modifier.padding(start = 16.dp, end = 16.dp),
                    maxLines = 1
                )

                Body3Secondary(
                    text = sportActivityItem.locationText,
                    Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp),
                    maxLines = 1
                )
            }
            Box(
                modifier = Modifier
                    .height(95.dp)
                    .width(95.dp)
                    .background(
                        color = AppResources.color(sportActivityItem.colorTypeSport),
                        shape = RoundedCornerShape(
                            topStart = 12.dp,
                            bottomEnd = 12.dp
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = AppResources.icon(sportActivityItem.iconTypeSport),
                    contentDescription = "icon activity",
                    tint = Tokens.BackgroundSecondary.getColor(),
                    modifier = Modifier
                        .size(50.dp)
                )
            }
        }
    }
}