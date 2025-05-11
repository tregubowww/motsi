package com.example.motsi.feature.login.impl.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.designsystem.buttons.mainbutton.ButtonStyle
import com.example.motsi.core.ui.designsystem.buttons.mainbutton.DoActionButton
import com.example.motsi.core.ui.theming.Body1Primary
import com.example.motsi.core.ui.theming.Title1Primary
import com.example.motsi.feature.login.impl.presentation.LoginViewModel
import kotlinx.coroutines.delay

//Скрин регистрации
@Composable
internal fun RegisterScreen(
    viewModel: LoginViewModel,
    onBackPressed: () -> Unit,
    onRegisterClicked: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password1 by rememberSaveable { mutableStateOf("") }
    var password2 by rememberSaveable { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    var lastCharVisible1 by remember { mutableStateOf(false) }
    var lastCharIndex1 by remember { mutableIntStateOf(-1) }
    var lastCharVisible2 by remember { mutableStateOf(false) }
    var lastCharIndex2 by remember { mutableIntStateOf(-1) }

    // Собор ошибок из ViewModel
    val usernameError by viewModel.usernameError.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val password1Error by viewModel.password1Error.collectAsState()
    val password2Error by viewModel.password2Error.collectAsState()

    LaunchedEffect(password1) {
        if (password1.isNotEmpty()) {
            lastCharIndex1 = password1.length - 1
            lastCharVisible1 = true
            delay(500)
            lastCharVisible1 = false
        }
    }

    LaunchedEffect(password2) {
        if (password2.isNotEmpty()) {
            lastCharIndex2 = password2.length - 1
            lastCharVisible2 = true
            delay(500)
            lastCharVisible2 = false
        }
    }

    val passwordTransformation1 = remember(isPasswordVisible, lastCharVisible1, lastCharIndex1) {
        if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            com.example.motsi.core.common.presentation.PasswordVisualTransformation(
                lastCharVisible1,
                lastCharIndex1
            )
        }
    }

    val passwordTransformation2 = remember(isPasswordVisible, lastCharVisible2, lastCharIndex2) {
        if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            com.example.motsi.core.common.presentation.PasswordVisualTransformation(
                lastCharVisible2,
                lastCharIndex2
            )
        }
    }

    val usernameRequired = stringResource(com.example.motsi.feature.login.impl.R.string.username_required)
    val invalidUsernameFormat = stringResource(com.example.motsi.feature.login.impl.R.string.invalid_username_format)
    val emailRequired = stringResource(com.example.motsi.feature.login.impl.R.string.email_required)
    val invalidEmailFormat = stringResource(com.example.motsi.feature.login.impl.R.string.invalid_email_format)
    val passwordRequired = stringResource(com.example.motsi.feature.login.impl.R.string.password_required)
    val weakPassword = stringResource(com.example.motsi.feature.login.impl.R.string.weak_password)
    val passwordRepeatRequired = stringResource(com.example.motsi.feature.login.impl.R.string.password_repeat_required)
    val passwordsMismatch = stringResource(com.example.motsi.feature.login.impl.R.string.passwords_mismatch)

    val errorMessages = remember {
        mapOf(
            "username_required" to usernameRequired,
            "invalid_username_format" to invalidUsernameFormat,
            "email_required" to emailRequired,
            "invalid_email_format" to invalidEmailFormat,
            "password_required" to passwordRequired,
            "weak_password" to weakPassword,
            "password_repeat_required" to passwordRepeatRequired,
            "passwords_mismatch" to passwordsMismatch
        )
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .imePadding()
        ) {

            IconButton(
                onClick = { onBackPressed() },
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
                    .padding(horizontal = 21.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                Title1Primary(
                    text = stringResource(com.example.motsi.feature.login.impl.R.string.register)
                )
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Body1Primary(stringResource(com.example.motsi.feature.login.impl.R.string.username)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = usernameError != null,
                    singleLine = true
                )
                if (usernameError != null) {
                    Text(
                        text = usernameError!!,
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )
                }

                // Поле "Email" с ошибкой
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Body1Primary(stringResource(com.example.motsi.feature.login.impl.R.string.email)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = emailError != null,
                    singleLine = true
                )
                if (emailError != null) {
                    Text(
                        text = emailError!!,
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )
                }

                TextField(
                    value = password1,
                    onValueChange = { password1 = it },
                    label = { Body1Primary(stringResource(com.example.motsi.feature.login.impl.R.string.crete_password)) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = passwordTransformation1,
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            val icon: Painter = if (isPasswordVisible) {
                                painterResource(id = R.drawable.ic_visibility)
                            } else {
                                painterResource(id = R.drawable.ic_visibility_off)
                            }
                            Icon(
                                painter = icon,
                                contentDescription = if (isPasswordVisible) {
                                    stringResource(id = com.example.motsi.feature.login.impl.R.string.close_password)
                                }
                                else stringResource(id = com.example.motsi.feature.login.impl.R.string.show_password)
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                    singleLine = true
                )
                if (password1Error != null) {
                    Text(
                        text = password1Error!!,
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )
                }

                TextField(
                    value = password2,
                    onValueChange = { password2 = it },
                    label = { Body1Primary(stringResource(com.example.motsi.feature.login.impl.R.string.repeat_password)) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = passwordTransformation2,
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            val icon: Painter = if (isPasswordVisible) {
                                painterResource(id = R.drawable.ic_visibility)
                            } else {
                                painterResource(id = R.drawable.ic_visibility_off)
                            }
                            Icon(
                                painter = icon,
                                contentDescription = if (isPasswordVisible) {
                                    stringResource(id = com.example.motsi.feature.login.impl.R.string.close_password)
                                }
                                else stringResource(id = com.example.motsi.feature.login.impl.R.string.show_password)
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                    singleLine = true
                )
                if (password2Error != null) {
                    Text(
                        text = password2Error!!,
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )
                }

                DoActionButton(
                    text = stringResource(com.example.motsi.feature.login.impl.R.string.register),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val isValid = viewModel.validateAndRegister(username, email, password1, password2, errorMessages)
                        if (isValid) {
                            onRegisterClicked()
                        }
                    },
                    style = ButtonStyle.BrandButton,
                    isEnabled = username.isNotEmpty() && email.isNotEmpty() && password1.isNotEmpty() && password2.isNotEmpty()
                )
            }
        }
    }
}