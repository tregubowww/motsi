package com.example.motsi.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.motsi.core.ui.designsystem.badge.Badge
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.feature.addsportactivity.api.AddSportActivityGraph
import com.example.motsi.feature.search.api.SearchGraph
import com.example.motsi.messages.api.MessagesGraph

@Composable
internal fun BottomNavBarWidget(
    navController: NavController,
    viewModel: MainViewModel,
) {
    val model by viewModel.mainModel.collectAsState()

    Row(
        Modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .height(64.dp)
            .background(color = Tokens.Background.getColor()),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        BottomBarIcon(
            navController = navController,
            route = SearchGraph,
            icon = com.example.motsi.core.ui.R.drawable.ic_home_fill_24dp,
            badge = null,
        )
        BottomBarIcon(
            navController = navController,
            route = MessagesGraph,
            icon = com.example.motsi.core.ui.R.drawable.ic_like_fill_24dp,
            badge = model.numberFavorites,
        )
        BottomBarIcon(
            navController = navController,
            route = AddSportActivityGraph,
            icon = com.example.motsi.core.ui.R.drawable.ic_add_fill_24dp,
            badge = null
        )
        BottomBarIcon(
            navController = navController,
            route = MessagesGraph,
            icon = com.example.motsi.core.ui.R.drawable.ic_message_fill_24dp,
            badge = model.numberMassages,
        )
        BottomBarIcon(
            navController = navController,
            route = MessagesGraph,
            icon = com.example.motsi.core.ui.R.drawable.ic_avatar_fill_24dp,
            badge = null,
        )
    }
}

@Composable
private fun BottomBarIcon(
    navController: NavController,
    route: Any,
    @DrawableRes
    icon: Int,
    badge: String?,
) {
    val entry by navController.currentBackStackEntryAsState()

    val isSelected = entry?.destination?.hierarchy?.any { it.hasRoute(route::class) } == true

    Box {
        Icon(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .clickable {
                    navController.navigate(route) {
                        popUpTo(navController.graph.id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                .padding(12.dp),
            painter = painterResource(icon),
            contentDescription = null,
            tint = if (isSelected) Tokens.IconBrand1.getColor() else Tokens.IconSecondary.getColor(),
        )
        if (badge != null) {
            Badge(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp),
                text = badge,
                color = Tokens.IconFavorites.getColor()
            )
        }
    }
}