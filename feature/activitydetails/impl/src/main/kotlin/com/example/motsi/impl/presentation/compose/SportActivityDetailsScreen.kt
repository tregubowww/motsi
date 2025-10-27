package com.example.motsi.impl.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.navigation.presentation.compose.LocalAppNavController
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.AppBarAction
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.BaseAppBar
import com.example.motsi.core.ui.designsystem.badge.Badge
import com.example.motsi.core.ui.designsystem.buttons.BaseButton
import com.example.motsi.core.ui.theming.AppResources
import com.example.motsi.core.ui.theming.Body3Primary
import com.example.motsi.core.ui.theming.Footnote2Secondary
import com.example.motsi.core.ui.theming.Title1Primary
import com.example.motsi.core.ui.theming.Footnote1Primary
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.core.ui.utils.LifecycleEffect
import com.example.motsi.impl.models.domain.SportActivityDetailsScreenModel
import com.example.motsi.impl.models.presentation.SportActivityDetailsScreenIntent
import com.example.motsi.impl.presentation.SportActivityDetailsViewModel

@Composable
internal fun SportActivityDetailsScreen(
    id: String,
    viewModel: SportActivityDetailsViewModel,
    bottomNavBar: @Composable () -> Unit,
) {
    val screenState by viewModel.screenState.collectAsState()
    LifecycleEffect(onCreate = { viewModel.initViewModel(id) })
    when (val state = screenState.loadingState) {
        is LoadingState.Loading -> {
            //            Loading()
        }

        is LoadingState.Success -> {
            Success(
                model = state.data,
                viewModel = viewModel,
                bottomNavBar = bottomNavBar,
            )
        }

        is LoadingState.Error -> {
//            Error()
        }

        else -> {
//            nothing
        }
    }
}

@Composable
private fun Success(
    model: SportActivityDetailsScreenModel,
    viewModel: SportActivityDetailsViewModel,
    bottomNavBar: @Composable () -> Unit,
) {
    val navController = LocalAppNavController.current
    val screenState by viewModel.screenState.collectAsState()

    Scaffold(
        modifier = Modifier,
        topBar = {
            BaseAppBar(
                navigationItem = AppBarAction(
                    iconRes = R.drawable.ic_back_24dp,
                    iconTint = Tokens.IconPrimary.getColor(),
                    onClick = { navController.popBackStack() }
                ),
                actions = setOf(
                    AppBarAction(
                        iconRes =
                            R.drawable.ic_send_24dp,
                        iconTint =
                            Tokens.IconPrimary.getColor(),
                        onClick = { viewModel.onScreenIntent(SportActivityDetailsScreenIntent.SendSportActivity) }
                    ),
                    AppBarAction(
                        iconRes = if (screenState.isFavorites) {
                            R.drawable.ic_like_fill_24dp
                        } else {
                            R.drawable.ic_like_24dp
                        },
                        iconTint = if (screenState.isFavorites) {
                            Tokens.IconFavorites.getColor()

                        } else {
                            Tokens.IconPrimary.getColor()
                        },
                        onClick = { viewModel.onScreenIntent(SportActivityDetailsScreenIntent.ChangeFavoritesStatus) }
                    ),
                )
            )
        },
        bottomBar = bottomNavBar
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {


            LazyRow(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(8.dp)) {
                items(model.participantList) { item ->
                    AsyncImage(
                        model = item.urlUserPic,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(110.dp)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Tokens.BackgroundSecondary.getColor()),
                    )
                }
            }


            Row(verticalAlignment = Alignment.CenterVertically) {
                Title1Primary(
                    text = model.typeSport,
                    maxLines = 1,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 8.dp,
                        bottom = 4.dp
                    )
                )
                if (model.privateStatus is SportActivityDetailsScreenModel.PrivateStatus.Private) {
                    Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                        Icon(
                            painter = AppResources.icon(model.privateStatus.icon),
                            contentDescription = null,
                            tint = Tokens.IconPrimary.getColor(),
                            modifier = Modifier
                                .size(24.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable(
                                    role = Role.Button,
                                    onClick = {
                                        viewModel.onScreenIntent(SportActivityDetailsScreenIntent.ShowInfoAboutPrivateStatus)
                                    }
                                )
                        )
                    }
                }
                if (model.level != null) {
                    Badge(
                        color =  AppResources.color(model.level.color),
                        text = model.level.text,
                        onClick = {
                            viewModel.onScreenIntent(SportActivityDetailsScreenIntent.ShowInfoLevelSportActivity)
                        }
                    )
                }
            }

            Footnote1Primary(
                text = model.typeSportActivity,
                maxLines = 1,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
            Body3Primary(
                text = model.typeSportActivityDescription,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
            Footnote2Secondary(
                text = model.dateText,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BaseButton(
                    modifier = Modifier.weight(1f),
                    text = model.buttons.titleChat,
                    color = Tokens.BackgroundBrand,
                    onClick = {
                        viewModel.onScreenIntent(SportActivityDetailsScreenIntent.OpenChatSportActivity)
                    }
                )
                BaseButton(
                    modifier = Modifier
                        .weight(1f)
                        ,
                    text = model.buttons.titleAddSportActivity,
                    color = Tokens.BackgroundBrand2,
                    onClick = {
                        viewModel.onScreenIntent(SportActivityDetailsScreenIntent.AddSportActivity)
                    }
                )
            }
        }
    }
}