package com.example.motsi.core.ui.designsystem.userpics

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.motsi.core.ui.theming.Tokens

@Composable
fun UserPics(
    modifier: Modifier = Modifier,
    iconSize: Int,
    urlPicsList: List<String>,
    maxItemCount: Int = 9,
    normalItemCount: Int = 6,
    onCLick: () -> Unit = {}
) {
    val sizeIndexFirst = iconSize / 1.5
    val sizeIndexSecond = iconSize / 3.3
    Box(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { onCLick() }) {
        urlPicsList.take(maxItemCount).forEachIndexed { index, item ->
            val offset = if (index < normalItemCount) {
                (index * sizeIndexFirst).dp
            } else {
                ((normalItemCount * sizeIndexFirst) + ((index - normalItemCount) * sizeIndexSecond)).dp
            }

            Box(
                modifier = Modifier
                    .offset(x = offset)
                    .zIndex((urlPicsList.size - index).toFloat())
                    .size(iconSize.dp)
                    .border(
                        width = 2.dp,
                        color = Tokens.BackgroundSecondary.getColor(),
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = item,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Tokens.BackgroundSecondary2.getColor()),
                    error = painterResource(com.example.motsi.core.ui.R.drawable.ic_avatar_fill_24dp)
                )
            }
        }
    }
}
