package com.example.motsi.feature.login.impl.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.motsi.core.common.presentation.PasswordVisualTransformation
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.designsystem.buttons.mainbutton.ButtonStyle
import com.example.motsi.core.ui.designsystem.buttons.mainbutton.DoActionButton
import com.example.motsi.core.ui.theming.Body1Primary
import com.example.motsi.core.ui.theming.Title1Primary
import com.example.motsi.feature.login.impl.presentation.view_model.AuthViewModel
import kotlinx.coroutines.delay

//Скрин входа по логину и паролю
@Composable
internal fun AuthScreen(
    viewModel: AuthViewModel,
    onBackPressed: () -> Unit
) {
    var login by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var lastCharVisible by remember { mutableStateOf(false) }
    var lastCharIndex by remember { mutableIntStateOf(-1) }
    var wrongLogin by remember { mutableStateOf(false) }

    LaunchedEffect(password) {
        if (password.isNotEmpty()) {
            lastCharIndex = password.length - 1
            lastCharVisible = true
            delay(500)
            lastCharVisible = false
        }
    }

    val passwordVisualTransformation = remember(isPasswordVisible, lastCharVisible, lastCharIndex) {
        if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation(lastCharVisible, lastCharIndex)
        }
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
                    text = stringResource(com.example.motsi.feature.login.impl.R.string.entrance)
                )

                TextField(
                    value = login,
                    onValueChange = { login = it },
                    label = { Body1Primary(stringResource(com.example.motsi.feature.login.impl.R.string.login)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Body1Primary(stringResource(com.example.motsi.feature.login.impl.R.string.password)) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = passwordVisualTransformation,
                    singleLine = true,
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
                )
                if (wrongLogin) {
                    Text(
                        text =stringResource(com.example.motsi.feature.login.impl.R.string.wrong_password_message),
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )
                }

                DoActionButton(
                    text = stringResource(com.example.motsi.feature.login.impl.R.string.enter),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val isValid = viewModel.validateAndLogin(login, password)
                        if (isValid) {
                            //Переход на главный экран приложения
                        } else {
                            wrongLogin = true
                        }
                    },
                    style = ButtonStyle.BrandButton,
                    isEnabled = login.isNotEmpty() && password.isNotEmpty()
                )
            }
        }
    }
}