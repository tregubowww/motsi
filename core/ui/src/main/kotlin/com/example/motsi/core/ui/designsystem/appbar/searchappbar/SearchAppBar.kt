package com.example.motsi.core.ui.designsystem.appbar.searchappbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    actions: List<AppBarAction> = emptyList(),
    hint: String? = null,
) {
    val horizontalPadding = 16.dp
    val barHeight = 58.dp

    Row(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .fillMaxWidth()
            .height(barHeight)
            .padding(horizontal = horizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        navigationItem?.let { item ->
            IconButton(onClick = item.onClick) {
                Icon(
                    painter = painterResource(id = item.iconRes),
                    contentDescription = item.iconContentDescription,
                    tint = item.iconTint
                )
            }
        }

        SearchField(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp, vertical = 6.dp),
            query = textSearch,
            onTextChange = onTextChange,
            hint = hint ?: stringResource(R.string.search_title),
            isNeedToFocused = isNeedToFocused,
            isEnabled = isEnabled,
            onSearchFieldClick = onSearchFieldClick,
            onKeyboardSearchButtonClick = onKeyboardSearchButtonClick
        )

        actions.forEach { action ->
            IconButton(onClick = action.onClick) {
                Icon(
                    painter = painterResource(id = action.iconRes),
                    contentDescription = action.iconContentDescription,
                    tint = action.iconTint
                )
            }
        }
    }
}