package com.example.motsi.feature.login.impl.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.motsi.core.navigation.presentation.compose.LocalAppNavController
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.designsystem.buttons.mainbutton.ButtonStyle
import com.example.motsi.core.ui.designsystem.buttons.mainbutton.DoActionButton
import com.example.motsi.core.ui.theming.Body1Primary
import com.example.motsi.core.ui.theming.MotsiTheme
import com.example.motsi.core.ui.theming.Text1Warning
import com.example.motsi.core.ui.theming.Title1Primary
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.feature.login.impl.domain.interactor.LoginInteractor
import com.example.motsi.feature.login.impl.presentation.LoginClickHandler
import com.example.motsi.feature.login.impl.presentation.view_model.RegisterViewModel
import kotlinx.coroutines.launch

//Скрин регистрации
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RegisterScreen(
    viewModel: RegisterViewModel,
    clickHandler: LoginClickHandler
) {

    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val navController = LocalAppNavController.current

    Scaffold(
        containerColor = Tokens.Background.getColor(),
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = Tokens.Background.getColor(),
                    actionIconContentColor = Tokens.TextPrimary.getColor(),
                    navigationIconContentColor = Tokens.TextPrimary.getColor(),
                    scrolledContainerColor = Tokens.Background.getColor(),
                    titleContentColor = Tokens.TextPrimary.getColor()
                    ),
                title = {
                    Title1Primary(
                        text = stringResource(com.example.motsi.feature.login.impl.R.string.register),
                        modifier = Modifier.padding(start = 70.dp)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { clickHandler.onBackPressed(navController) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_24),
                            contentDescription = stringResource(com.example.motsi.feature.login.impl.R.string.back)
                        )
                    }
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .imePadding()
            ) {
                DoActionButton(
                    text = stringResource(com.example.motsi.feature.login.impl.R.string.register),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = {
                        viewModel.register { success ->
                            if (success) {
                                clickHandler.onRegisterSuccess(navController)
                            }
                        }
                    },
                    style = ButtonStyle.BrandButton
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .imePadding()
                .padding(horizontal = 16.dp)
        ) {
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
                isPasswordVisible = state.isPassword1Visible,
                onToggleVisibility = viewModel::togglePasswordVisibility1
            )

            PasswordFieldSection(
                label = stringResource(com.example.motsi.feature.login.impl.R.string.repeat_password),
                value = state.password2,
                onValueChange = viewModel::updatePassword2,
                isError = state.password2Error != null,
                errorMessage = state.password2Error,
                transformation = state.passwordTransformation2,
                isPasswordVisible = state.isPassword2Visible,
                onToggleVisibility = viewModel::togglePasswordVisibility2
            )
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
        modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Tokens.TextFieldPrimary.getColor(),
                shape = MotsiTheme.roundedShapes.doActionButtonRoundedShape
            )
            .border(
                width = 1.dp,
                color = if (isError) Tokens.Warning.getColor()
                else Tokens.TextFieldPrimary.getColor(),
                shape = MotsiTheme.roundedShapes.doActionButtonRoundedShape
            )
    ) {
        BasicTextField(
            value = value.trim(),
            onValueChange = { newText ->
                val filteredText = newText.replace(" ", "")
                if (filteredText != newText) {
                    onValueChange(filteredText)
                } else {
                    onValueChange(newText)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            textStyle = MotsiTheme.textAppearance.Body1,
            singleLine = true
        )
    }
    errorMessage?.let {
        Text1Warning(
            text = it,
            modifier = Modifier.fillMaxWidth().padding(top = 5.dp))
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
        modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Tokens.TextFieldPrimary.getColor(),
                shape = MotsiTheme.roundedShapes.doActionButtonRoundedShape
            )
            .border(
                width = 1.dp,
                color = if (isError) Tokens.Warning.getColor()
                else Tokens.TextFieldPrimary.getColor(),
                shape = MotsiTheme.roundedShapes.doActionButtonRoundedShape
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            BasicTextField(
                value = value.trim(),
                onValueChange = { newText ->
                    val filteredText = newText.replace(" ", "")
                    if (filteredText != newText) {
                        onValueChange(filteredText)
                    } else {
                        onValueChange(newText)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp, vertical = 12.dp),
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
        Text1Warning(
            text = it,
            modifier = Modifier.fillMaxWidth().padding(top = 5.dp))
    }
}