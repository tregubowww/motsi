package com.example.motsi.feature.userprofile.impl.presentation.compose.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.theming.Headline2Primary
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.feature.userprofile.impl.models.domain.UserProfileScreenModel
import kotlinx.coroutines.launch

@Composable
internal fun UserPhotoWidget(
    screenModel: UserProfileScreenModel
) {
    val userPics = screenModel.userProfilePicList
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { userPics.size })

    LaunchedEffect(Unit) {
        pagerState.scrollToPage(0)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenWidth)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    when {
                        offset.x < size.width / 2 -> {
                            // Тап по левой половине - предыдущее фото
                            coroutineScope.launch {
                                pagerState.scrollToPage(
                                    page = (pagerState.currentPage - 1 + userPics.size) % userPics.size
                                )
                            }
                        }

                        offset.x > size.width / 2 -> {
                            // Тап по правой половине - следующее фото
                            coroutineScope.launch {
                                pagerState.scrollToPage(
                                    page = (pagerState.currentPage + 1) % userPics.size
                                )
                            }
                        }
                    }
                }
            }
    ) {
        HorizontalPager(
            state = pagerState
        ) { item ->
            AsyncImage(
                model = userPics[item % userPics.size].urlPic,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                error = painterResource(R.drawable.ic_default_avatar_24dp),
                placeholder = painterResource(R.drawable.ic_default_avatar_24dp)
            )
        }

        //Индикатор
        if (userPics.size > 1) {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(userPics.size) { index ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(2.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(
                                color = if (pagerState.currentPage == index) {
                                    Tokens.IconWhite.getColor()
                                } else {
                                    Tokens.IconWhite.getColor().copy(alpha = 0.5f)
                                }
                            )
                    )
                }
            }

            //Размытие
            BlurBox(
                modifier = Modifier.align(Alignment.BottomStart),
                screenWidth = screenWidth
            )

            Headline2Primary(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                text = screenModel.userInformation.name, maxLines = 1
            )
        }
    }
}

@Composable
private fun BlurBox(
    modifier: Modifier,
    screenWidth: Dp
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(screenWidth / 5)
            .background(
                brush = Brush.verticalGradient(
                    0.0f to Tokens.BackgroundGray.getColor().copy(alpha = 0f),
                    0.2f to Tokens.BackgroundGray.getColor().copy(alpha = 0.4f),
                    0.4f to Tokens.BackgroundGray.getColor().copy(alpha = 0.6f),
                    0.6f to Tokens.BackgroundGray.getColor().copy(alpha = 0.8f),
                    1.0f to Tokens.BackgroundGray.getColor()
                )
            )
    )
}