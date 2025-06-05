package com.example.motsi.core.ui.designsystem.appbar.searchappbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.R

@Composable
fun SearchAppBar(
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit = {},
    navigationItem: AppBarAction? = null,
    textSearch: String = "",
    isNeedToFocused: Boolean = true,
    isEnabled: Boolean = true,
    onSearchClicked: (String) -> Unit = {},
    actions: Set<AppBarAction> = emptySet(),
    hint: String?,
) {
    Row(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (navigationItem != null) {
            Icon(
                painter = painterResource(id = navigationItem.iconRes),
                tint = navigationItem.iconTint,
                contentDescription = navigationItem.iconContentDescription,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable(
                        role = Role.Button,
                        onClick = { navigationItem.onClick.invoke() }
                    )
            )
        }


        SearchField(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            query = textSearch,
            onTextChange = { onTextChange(it) },
            hint = hint?: stringResource(R.string.search_title),
            isNeedToFocused = isNeedToFocused,
            isEnabled = isEnabled,
            onSearchClicked = { onSearchClicked(it) }
        )

        Row(
            modifier = Modifier.padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            actions.forEach {
                Icon(
                    painter = painterResource(id = it.iconRes),
                    tint = it.iconTint,
                    contentDescription = it.iconContentDescription,
                    modifier = Modifier
                        .clickable(
                            role = Role.Button,
                            onClick = { it.onClick.invoke() }
                        )
                )
            }
        }
    }
}
