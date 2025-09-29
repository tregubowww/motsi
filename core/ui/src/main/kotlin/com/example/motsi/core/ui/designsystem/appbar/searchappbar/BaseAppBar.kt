package com.example.motsi.core.ui.designsystem.appbar.searchappbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun BaseAppBar(
    modifier: Modifier = Modifier,
    navigationItem: AppBarAction? = null,
    actions: Set<AppBarAction> = emptySet(),
) {

    Row(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .fillMaxWidth()
            .height(58.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
    ) {
        if (navigationItem != null) {
            Icon(
                painter = painterResource(id = navigationItem.iconRes),
                tint = navigationItem.iconTint,
                contentDescription = navigationItem.iconContentDescription,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(24.dp)
                    .clickable(
                        role = Role.Button,
                        onClick = { navigationItem.onClick.invoke() }
                    )
            )
        }
        Row(modifier = Modifier.padding(end = 16.dp)) {
            actions.forEach {
                Icon(
                    painter = painterResource(id = it.iconRes),
                    tint = it.iconTint,
                    contentDescription = it.iconContentDescription,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(24.dp)
                        .clickable(
                            role = Role.Button,
                            onClick = { it.onClick.invoke() }
                        )
                )
            }
        }
    }
}