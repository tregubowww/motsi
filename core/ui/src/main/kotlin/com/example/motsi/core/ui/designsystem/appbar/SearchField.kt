package com.example.motsi.core.ui.designsystem.appbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.theming.Title1Primary

@Composable
fun SearchField(
    query: String,
    onTextChange1: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String,
    isEnabled: Boolean = true,
    isNeedToFocused: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    onCloseClicked: () -> Unit = {},
    onSearchClicked: (String) -> Unit = {}
) {


    val focusRequester = remember { FocusRequester() }

    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = query)) }
    val textFieldValue = textFieldValueState.copy(text = query)
    SideEffect {
        if (textFieldValue.selection != textFieldValueState.selection ||
            textFieldValue.composition != textFieldValueState.composition
        ) {

            textFieldValueState = textFieldValue
        }
    }

    var lastTextValue by remember(query) { mutableStateOf(query) }
    val text = textFieldValue.text


    LaunchedEffect(Unit) {
        if (isNeedToFocused) {
            focusRequester.requestFocus()
        }
    }

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .height(40.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(12.dp)
            )
    ) {

        if (text.isEmpty()) {
            Title1Primary(
                hint,
                Modifier
                    .padding(horizontal = 40.dp)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.padding(start = 16.dp),
                painter = painterResource(R.drawable.ic_search_20dp),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondary
            )

            BasicTextField(
                value = textFieldValue,
                onValueChange = { newTextFieldValueState: TextFieldValue ->
                    textFieldValueState = newTextFieldValueState
                    val stringChangedSinceLastInvocation =
                        lastTextValue != newTextFieldValueState.text
                    lastTextValue = newTextFieldValueState.text

                    if (stringChangedSinceLastInvocation) {
                        onTextChange1(newTextFieldValueState.text)
                    }
                },
                modifier = Modifier
                    .padding(start = 4.dp)
                    .weight(1f)
                    .focusRequester(focusRequester),
                enabled = isEnabled,
                singleLine = true,
                textStyle = TextStyle.Default,
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(
                    onSearch = { onSearchClicked(query) }
                ),
            )

            AnimatedVisibility(visible = text.isNotEmpty() && isEnabled) {
                ClearButton(text, onTextChange1, onCloseClicked)
            }
        }
    }
}

@Composable
private fun ClearButton(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 8.dp) {
        IconButton(
            onClick = {
                if (text.isNotEmpty()) {
                    onTextChange("")
                    onCloseClicked()
                }
            }
        ) {
            Icon(
                modifier = Modifier.padding(end = 16.dp),
                painter = painterResource(id = R.drawable.ic_cross_20dp),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
