package com.example.motsi.core.ui.designsystem.fields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.designsystem.divider.SoftDivider
import com.example.motsi.core.ui.designsystem.userpics.UserPics
import com.example.motsi.core.ui.models.ItemSportActivityButton
import com.example.motsi.core.ui.theming.Body3Primary
import com.example.motsi.core.ui.theming.Body3Secondary
import com.example.motsi.core.ui.theming.Title1Primary
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ItemSportActivity(
    modifier: Modifier = Modifier,
    urlPicsList: List<String>,
    title: String,
    onClickItem: () -> Unit,
    subtitle: String? = null,
    subtitleIcon: ItemSportActivityButton? = null,
    description: String? = null,
    rightButtons: ImmutableList<ItemSportActivityButton> = persistentListOf(),
    logo: ItemSportActivityButton? = null
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClickItem() },
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            UserPics(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                iconSize = 50,
                urlPicsList = urlPicsList
            )
            Column {
                rightButtons.forEach {
                    Icon(
                        painter = it.icon,
                        contentDescription = it.contentDescription,
                        tint = it.tint,
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .size(28.dp)
                            .clickable(role = Role.Button) {
                                it.onClick.invoke()
                            }
                    )
                }
            }
        }
        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {

                Title1Primary(
                    text = title,
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    maxLines = 1
                )

                Row(verticalAlignment = Alignment.CenterVertically) {

                    if (subtitle != null){
                        Body3Primary(
                            text = subtitle,
                            Modifier.padding(start = 16.dp, end = 16.dp),
                            maxLines = 1
                        )
                    }
                    if (subtitleIcon != null) {
                        Icon(
                            painter = subtitleIcon.icon ,
                            contentDescription = subtitleIcon.contentDescription,
                            tint = subtitleIcon.tint,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                if (description != null){
                    Body3Secondary(
                        text = description,
                        Modifier.padding(start = 16.dp, end = 16.dp),
                        maxLines = 2
                    )
                }
            }
            if (logo != null){
                Icon(
                    painter = logo.icon ,
                    contentDescription = logo.contentDescription,
                    tint = logo.tint,
                    modifier = Modifier.padding(16.dp)
                        .size(60.dp)
                )
            }
        }
        Spacer(Modifier.padding(top = 8.dp))
        SoftDivider()
    }
}