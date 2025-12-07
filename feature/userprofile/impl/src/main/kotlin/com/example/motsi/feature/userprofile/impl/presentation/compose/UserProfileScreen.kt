package com.example.motsi.feature.userprofile.impl.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.AppBarAction
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.BaseAppBar
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.core.ui.utils.CollectEffect
import com.example.motsi.feature.userprofile.impl.models.domain.UserProfileScreenModel
import com.example.motsi.feature.userprofile.impl.models.presentation.UserProfileScreenEffect
import com.example.motsi.feature.userprofile.impl.models.presentation.UserProfileScreenIntent
import com.example.motsi.feature.userprofile.impl.presentation.UserProfileViewModel
import com.example.motsi.feature.userprofile.impl.presentation.compose.widgets.FollowersWidget
import com.example.motsi.feature.userprofile.impl.presentation.compose.widgets.SportTypesWidget
import com.example.motsi.feature.userprofile.impl.presentation.compose.widgets.UserInformationWidget
import com.example.motsi.feature.userprofile.impl.presentation.compose.widgets.UserPhotoWidget

@Composable
internal fun UserProfileScreen(
    viewModel: UserProfileViewModel,
    bottomNavBar: @Composable () -> Unit
) {
    val screenState by viewModel.screenState.collectAsState()
    when (val state = screenState.loadingState) {
        is LoadingState.Loading -> {
            //            Loading()
        }

        is LoadingState.Success -> {
            SuccessUserProfileScreen(
                screenModel = state.data,
                bottomNavBar = bottomNavBar,
                viewModel = viewModel
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
private fun SuccessUserProfileScreen(
    screenModel: UserProfileScreenModel,
    bottomNavBar: @Composable () -> Unit,
    viewModel: UserProfileViewModel,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier,
        topBar = {
            BaseAppBar(
                modifier = Modifier.background(color = Tokens.Background.getColor()),
                title = screenModel.userInformation.name,
                actions = setOf(
                    AppBarAction(
                        iconRes = R.drawable.ic_send_24dp,
                        iconTint = Tokens.IconPrimary.getColor(),
                        onClick = {
                            viewModel.dispatch(
                                UserProfileScreenIntent.ClickOnSendUserProfile(screenModel.userInformation.url)
                            )
                        }
                    ),
                    AppBarAction(
                        iconRes = R.drawable.ic_edit_fill_24dp,
                        iconTint = Tokens.IconPrimary.getColor(),
                        onClick = {
                            viewModel.dispatch(
                                UserProfileScreenIntent.ClickOnEditUserProfileScreen
                            )
                        }
                    ))
            )
        },
        bottomBar = bottomNavBar,
    ) { padding ->
        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                is UserProfileScreenEffect.NavigateToSubscriptionsScreen -> {
                    /*TODO() переход на экран SubscriptionsScreen*/
//                            navController.navigate(SubscriptionsScreenDestination)
                }

                is UserProfileScreenEffect.NavigateToSubscribersScreen -> {
                    /*TODO() переход на экран SubscribersScreen*/
//                            navController.navigate(SubscribersScreenDestination)
                }

                is UserProfileScreenEffect.NavigateToEditUserProfileScreen -> {
                    /*TODO() переход на экран EditUserProfileScreen*/
//                            navController.navigate(EditUserProfileScreenDestination)
                }

                is UserProfileScreenEffect.SendUserProfile -> {
                    /*TODO() отправка профиля по effect.userId*/
                }

                is UserProfileScreenEffect.NavigateToSportTypeLevelInfoScreen -> {
                    /*TODO открыть экран информационной шторы, рассказывающей о рейтинговой системе*/
                }
            }
        }

        Column(
            modifier = Modifier
                .background(Tokens.BackgroundSecondary.getColor())
                .padding(padding)
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
            UserPhotoWidget(screenModel = screenModel)
            FollowersWidget(screenModel = screenModel, viewModel = viewModel)
            SportTypesWidget(screenModel = screenModel, viewModel = viewModel)
            UserInformationWidget(userInformation = screenModel.userInformation)
        }
    }
}


