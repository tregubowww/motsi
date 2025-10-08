package com.example.motsi.core.ui.designsystem.appbar.searchappbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.theming.Subtitle1Secondary
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
    onKeyboardSearchButtonClick: (String) -> Unit = {},
    onFilterClick: () -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    var textFieldValueState by remember {
        mutableStateOf(TextFieldValue(text = query))
    }

    // Поддержка внешнего обновления query
    if (textFieldValueState.text != query) {
        textFieldValueState =
            textFieldValueState.copy(text = query, selection = TextRange(query.length))
    }

    LaunchedEffect(isNeedToFocused) {
        if (isNeedToFocused) {
            focusRequester.requestFocus()
        }
    }

    Row(
        modifier = modifier
            .testTag("SearchField")
            .fillMaxSize()
            .background(
                color = Tokens.BackgroundSecondary.getColor(),
                shape = RoundedCornerShape(12.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(start = 16.dp),
            painter = painterResource(R.drawable.ic_search_20dp),
            contentDescription = null,
            tint = if (textFieldValueState.text.isEmpty()) {
                Tokens.IconSecondary.getColor()
            } else Tokens.IconPrimaryReverse.getColor()
        )

        BasicTextField(
            value = textFieldValueState,
            onValueChange = {
                textFieldValueState = it
                onTextChange(it.text)
            },
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(1f)
                .focusRequester(focusRequester),
            enabled = isEnabled,
            singleLine = true,
            // Почему не нашa текстовка?
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = Tokens.TextPrimary.getColor()
            ),
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onSearch = { onKeyboardSearchButtonClick(query) }
            ),
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (textFieldValueState.text.isEmpty()) {
                        Subtitle1Secondary(
                            text = hint
                        )
                    }
                    innerTextField()
                }
            }
        )

        AnimatedVisibility(visible = textFieldValueState.text.isNotEmpty() && isEnabled) {
            ClearButton(
                text = textFieldValueState.text,
                onTextChange = onTextChange,
                onCloseClick = onCloseClick
            )
        }

        if (!isEnabled) {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable(onClick = onFilterClick),
                painter = painterResource(R.drawable.ic_filter_24dp),
                contentDescription = null,
                tint = Tokens.IconPrimary.getColor(),
            )
        }
    }
}

@Composable
private fun ClearButton(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClick: () -> Unit,
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 8.dp) {
        IconButton(
            modifier = Modifier
                .testTag("ClearButton"),
            onClick = {
                if (text.isNotEmpty()) {
                    onTextChange("")
                    onCloseClick()
                }
            }
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 16.dp),
                painter = painterResource(id = R.drawable.ic_cross_outline_24dp),
                contentDescription = null,
                tint = Tokens.IconPrimary.getColor()
            )
        }
    }
}