package com.example.motsi.feature.userprofile.impl.presentation.compose.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.designsystem.baseline.HorizontalLine
import com.example.motsi.core.ui.theming.AppResources
import com.example.motsi.core.ui.theming.Body1Primary
import com.example.motsi.core.ui.theming.Body3Brand
import com.example.motsi.core.ui.theming.Body3Primary
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.feature.userprofile.impl.models.domain.UserProfileScreenModel
import com.example.motsi.feature.userprofile.impl.models.presentation.UserProfileScreenIntent
import com.example.motsi.feature.userprofile.impl.presentation.UserProfileViewModel

@Composable
internal fun SportTypesWidget(
    screenModel: UserProfileScreenModel,
    viewModel: UserProfileViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .background(color = Tokens.Background.getColor(), shape = RoundedCornerShape(12.dp))
    ) {
        screenModel.sportTypeList.forEachIndexed { index, sportType ->
            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .padding(end = 16.dp, start = 16.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.clickable {
                        viewModel.dispatch(
                            UserProfileScreenIntent.ClickOnLevel
                        )
                    }) {
                        Body3Primary(
                            text = sportType.type,
                            maxLines = 1,
                            modifier = Modifier.alignByBaseline().weight(1f, fill = false)
                        )
                        sportType.rating?.let { rating ->
                            Icon(
                                painter = painterResource(R.drawable.ic_star_fill_24dp),
                                contentDescription = null,
                                tint = Tokens.IconRating.getColor(),
                                modifier = Modifier
                                    .padding(horizontal = 6.dp)
                                    .size(16.dp)
                                    .alignBy { it.measuredHeight }
                            )
                            Body3Primary(text = rating, modifier = Modifier.alignByBaseline())
                        }
                    }
                    Row(verticalAlignment = Alignment.Bottom) {
                        Body1Primary(
                            modifier = Modifier
                                .padding(end = 6.dp)
                                .alignByBaseline(),
                            text = screenModel.sportActivityTitle
                        )
                        Body3Brand(
                            text = sportType.count,
                            modifier = Modifier.alignByBaseline()
                        )
                    }

                }
                Icon(
                    painter = AppResources.icon(sportType.urlPic),
                    contentDescription = null,
                    tint = Tokens.Background.getColor(),
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = Tokens.BackgroundSecondary2.getColor(),
                            shape = CircleShape
                        )
                        .wrapContentSize(Alignment.Center)
                        .then(Modifier.size(30.dp))
                )

            }
            if (index != screenModel.sportTypeList.lastIndex) {
                HorizontalLine(modifier = Modifier.padding(start = 16.dp))
            }
        }
    }
}