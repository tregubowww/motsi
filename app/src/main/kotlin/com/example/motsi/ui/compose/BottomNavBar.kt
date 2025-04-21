package com.example.motsi.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.motsi.feature.login.api.LoginGraph
import com.example.motsi.feature.search.api.SearchGraph
import com.example.motsi.messages.api.MessagesGraph

@Composable
internal fun BottomNavBar(
    navController: NavController
) {
    val entry by navController.currentBackStackEntryAsState()
    val currentDestination = entry?.destination

    Row(
        Modifier
            .fillMaxWidth()
            .height(70.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        getNavBottomBarItems().forEach { item ->
            BottomBarIcon(
                selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true,
                glowingIcon = item.icon,
                idleIcon = item.icon,
                modifier = Modifier.clickable {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
private fun BottomBarIcon(
    selected: Boolean,
    glowingIcon: ImageVector,
    idleIcon: ImageVector,
    modifier: Modifier = Modifier,
    iconColor: Color = Color.Gray,
    glowColor: Color = Color.Blue,
) {
    val icon = if (selected) glowingIcon else idleIcon

    Icon(
        modifier = modifier,
        imageVector = icon,
        contentDescription = null,
        tint = if (selected) glowColor else iconColor
    )
}

private data class NavBottomBarItem(
    val route: Any,
    val icon: ImageVector,
)

private fun getNavBottomBarItems() = listOf(
    NavBottomBarItem(
        route = SearchGraph,
        icon = Icons.Rounded.Home,
    ),
    NavBottomBarItem(
        route = MessagesGraph,
        icon = Icons.Rounded.Send,
    )
)

