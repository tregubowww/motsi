package com.example.motsi.feature.login.impl.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.designsystem.buttons.mainbutton.ButtonStyle
import com.example.motsi.core.ui.designsystem.buttons.mainbutton.DoActionButton
import com.example.motsi.core.ui.theming.Body1Primary
import com.example.motsi.core.ui.theming.MotsiTheme
import com.example.motsi.core.ui.theming.Text1Warning
import com.example.motsi.core.ui.theming.Title1Primary
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.feature.login.impl.presentation.view_model.RegisterViewModel

//Скрин регистрации
@Composable
internal fun RegisterScreen(
    viewModel: RegisterViewModel,
    onBackPressed: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .imePadding()
        ) {
            IconButton(
                onClick = onBackPressed,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_24),
                    contentDescription = stringResource(com.example.motsi.feature.login.impl.R.string.back)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.TopStart),
                horizontalAlignment = Alignment.Start
            ) {
                Title1Primary(
                    text = stringResource(com.example.motsi.feature.login.impl.R.string.register),
                    modifier = Modifier.padding(horizontal = 70.dp, vertical = 21.dp)
                )

                // Username Field
                TextFieldSection(
                    label = stringResource(com.example.motsi.feature.login.impl.R.string.username),
                    value = state.username,
                    onValueChange = viewModel::updateUsername,
                    isError = state.usernameError != null,
                    errorMessage = state.usernameError
                )

                // Email Field
                TextFieldSection(
                    label = stringResource(com.example.motsi.feature.login.impl.R.string.email),
                    value = state.email,
                    onValueChange = viewModel::updateEmail,
                    isError = state.emailError != null,
                    errorMessage = state.emailError
                )

                // Password Fields
                PasswordFieldSection(
                    label = stringResource(com.example.motsi.feature.login.impl.R.string.create_password),
                    value = state.password1,
                    onValueChange = viewModel::updatePassword1,
                    isError = state.password1Error != null,
                    errorMessage = state.password1Error,
                    transformation = state.passwordTransformation1,
                    isPasswordVisible = state.isPasswordVisible,
                    onToggleVisibility = viewModel::togglePasswordVisibility
                )

                PasswordFieldSection(
                    label = stringResource(com.example.motsi.feature.login.impl.R.string.repeat_password),
                    value = state.password2,
                    onValueChange = viewModel::updatePassword2,
                    isError = state.password2Error != null,
                    errorMessage = state.password2Error,
                    transformation = state.passwordTransformation2,
                    isPasswordVisible = state.isPasswordVisible,
                    onToggleVisibility = viewModel::togglePasswordVisibility
                )

                DoActionButton(
                    text = stringResource(com.example.motsi.feature.login.impl.R.string.register),
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    onClick = {
                        if (viewModel.validateAndRegister()) {
                            onRegisterSuccess()
                        }
                    },
                    style = ButtonStyle.BrandButton
                )

                state.error?.let { error ->
                    Text1Warning(text = error, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp))
                }
            }
        }
    }
}

@Composable
private fun TextFieldSection(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String?
) {
    Body1Primary(
        text = label,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Tokens.TextFieldPrimary.getColor(),
                shape = MotsiTheme.roundedShapes.small
            )
            .border(
                width = 1.dp,
                color = if (isError) Tokens.Warning.getColor()
                else Tokens.TextFieldPrimary.getColor(),
                shape = MotsiTheme.roundedShapes.small
            )
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            textStyle = MotsiTheme.textAppearance.Body1,
            singleLine = true
        )
    }
    errorMessage?.let {
        Text1Warning(text = it, modifier = Modifier.fillMaxWidth().padding(start = 16.dp))
    }
}

@Composable
private fun PasswordFieldSection(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String?,
    transformation: VisualTransformation,
    isPasswordVisible: Boolean,
    onToggleVisibility: () -> Unit
) {
    Body1Primary(
        text = label,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Tokens.TextFieldPrimary.getColor(),
                shape = MotsiTheme.roundedShapes.small
            )
            .border(
                width = 1.dp,
                color = if (isError) Tokens.Warning.getColor()
                else Tokens.TextFieldPrimary.getColor(),
                shape = MotsiTheme.roundedShapes.small
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                textStyle = MotsiTheme.textAppearance.Body1.copy(
                    color = Tokens.TextPrimary.getColor()
                ),
                visualTransformation = transformation,
                singleLine = true,
                decorationBox = { it() }
            )

            IconButton(
                onClick = onToggleVisibility,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isPasswordVisible) R.drawable.ic_visibility
                        else R.drawable.ic_visibility_off
                    ),
                    contentDescription = if (isPasswordVisible) stringResource(com.example.motsi.feature.login.impl.R.string.hide_password)
                    else stringResource(com.example.motsi.feature.login.impl.R.string.show_password),
                    tint = Tokens.TextPrimary.getColor()
                )
            }
        }
    }
    errorMessage?.let {
        Text1Warning(text = it, modifier = Modifier.fillMaxWidth().padding(start = 16.dp))
    }
}