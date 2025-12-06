package com.example.motsi.feature.userprofile.impl.presentation.compose.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.designsystem.baseline.VerticalLine
import com.example.motsi.core.ui.designsystem.userpics.UserPics
import com.example.motsi.core.ui.theming.Body3Primary
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.feature.userprofile.impl.models.domain.UserProfileScreenModel
import com.example.motsi.feature.userprofile.impl.models.presentation.UserProfileScreenIntent
import com.example.motsi.feature.userprofile.impl.presentation.UserProfileViewModel

@Composable
internal fun FollowersWidget(
    screenModel: UserProfileScreenModel,
    viewModel: UserProfileViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .background(color = Tokens.Background.getColor(), shape = RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            Body3Primary(
                text = screenModel.subscriptions.title, maxLines = 1
            )
            Row(
                modifier = Modifier.padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Body3Primary(
                    text = viewModel.formatNumberFollowers(screenModel.subscriptions.count),
                    modifier = Modifier.padding(end = 4.dp)
                )
                UserPics(
                    iconSize = 30,
                    normalItemCount = 2,
                    maxItemCount = 6,
                    urlPicsList = screenModel.subscriptions.picList.map { it.urlPic },
                    onCLick = {
                        viewModel.dispatch(
                            UserProfileScreenIntent.ClickOnSubscriptionsPickList
                        )
                    }
                )
            }
        }

        VerticalLine(modifier = Modifier.height(70.dp).align(Alignment.CenterVertically))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            Body3Primary(
                text = screenModel.subscribers.title, maxLines = 1
            )
            Row(modifier = Modifier.padding(top = 10.dp)) {
                Body3Primary(
                    text = viewModel.formatNumberFollowers(screenModel.subscribers.count),
                    modifier = Modifier.padding(end = 4.dp)
                )
                UserPics(
                    iconSize = 30,
                    normalItemCount = 2,
                    maxItemCount = 6,
                    urlPicsList = screenModel.subscribers.picList.map { it.urlPic },
                    onCLick = {
                        viewModel.dispatch(
                            UserProfileScreenIntent.ClickOnSubscribersPickList
                        )
                    }
                )
            }
        }
    }
}