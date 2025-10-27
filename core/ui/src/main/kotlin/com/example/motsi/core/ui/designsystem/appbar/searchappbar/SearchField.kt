package com.example.motsi.core.ui.designsystem.appbar.searchappbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.theming.MotsiTheme
import com.example.motsi.core.ui.theming.Body3Secondary
import com.example.motsi.core.ui.theming.Tokens

@Composable
fun SearchField(
    query: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String,
    isEnabled: Boolean = true,
    isNeedToFocused: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    onCloseClick: () -> Unit = {},
    onSearchFieldClick: () -> Unit = {},
    onKeyboardSearchButtonClick: (String) -> Unit = {},
    onFilterClick: () -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    var textFieldValue by remember { mutableStateOf(TextFieldValue(query)) }

    // Синхронизация с внешним состоянием query
    LaunchedEffect(query) {
        if (query != textFieldValue.text) {
            textFieldValue = TextFieldValue(query, TextRange(query.length))
        }
    }

    // Фокусировка при первом появлении
    LaunchedEffect(isNeedToFocused) {
        if (isNeedToFocused) {
            focusRequester.requestFocus()
        }
    }

    Row(
        modifier = modifier
            .testTag("SearchField")
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                onClick = onSearchFieldClick,
                role = Role.Button
            )
            .background(Tokens.BackgroundSecondary.getColor()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_search_24dp),
            contentDescription = null,
            tint = if (textFieldValue.text.isEmpty())
                Tokens.IconSecondary.getColor()
            else
                Tokens.IconPrimaryReverse.getColor(),
            modifier = Modifier
                .padding(start = 16.dp)
                .size(20.dp)
        )

        BasicTextField(
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
                onTextChange(it.text)
            },
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(1f)
                .focusRequester(focusRequester),
            enabled = isEnabled,
            singleLine = true,
            textStyle = MotsiTheme.textAppearance.Body2.copy(color = Tokens.TextPrimary.getColor()),
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onSearch = {
                    onKeyboardSearchButtonClick(textFieldValue.text)
                }
            ),
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (textFieldValue.text.isEmpty()) {
                        Body3Secondary(text = hint)
                    }
                    innerTextField()
                }
            }
        )

        AnimatedVisibility(
            visible = textFieldValue.text.isNotEmpty() && isEnabled
        ) {
            ClearButton(
                onClear = {
                    textFieldValue = TextFieldValue("")
                    onTextChange("")
                    onCloseClick()
                }
            )
        }

        if (!isEnabled) {
            Icon(
                painter = painterResource(R.drawable.ic_filter_24dp),
                contentDescription = null,
                tint = Tokens.IconPrimary.getColor(),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable(onClick = onFilterClick)
            )
        }
    }
}

@Composable
private fun ClearButton(onClear: () -> Unit) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 8.dp) {
        IconButton(
            modifier = Modifier.testTag("ClearButton"),
            onClick = onClear
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cross_24dp),
                contentDescription = null,
                tint = Tokens.IconPrimary.getColor(),
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(20.dp)
            )
        }
    }
}