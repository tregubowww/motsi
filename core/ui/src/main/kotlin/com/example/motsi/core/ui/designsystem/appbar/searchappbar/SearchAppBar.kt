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
    onKeyboardSearchButtonClick: (String) -> Unit = {},
    onSearchFieldClick: () -> Unit = {},
    actions: Set<AppBarAction> = emptySet(),
    hint: String?,
) {

    Row(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .fillMaxWidth()
            .height(58.dp),
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
                .clickable(onClick = onSearchFieldClick),
            query = textSearch,
            onTextChange = { onTextChange(it) },
            hint = hint ?: stringResource(R.string.search_title),
            isNeedToFocused = isNeedToFocused,
            isEnabled = isEnabled,
            onKeyboardSearchButtonClick = { onKeyboardSearchButtonClick(it) }
        )

        actions.forEach {
            Icon(
                painter = painterResource(id = it.iconRes),
                tint = it.iconTint,
                contentDescription = it.iconContentDescription,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable(
                        role = Role.Button,
                        onClick = { it.onClick.invoke() }
                    )
            )
        }
    }
}