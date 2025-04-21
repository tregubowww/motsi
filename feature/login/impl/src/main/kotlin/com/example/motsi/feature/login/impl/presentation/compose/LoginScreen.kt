package com.example.motsi.feature.login.impl.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.designsystem.buttons.mainbutton.ButtonBorderStyle
import com.example.motsi.core.ui.theming.Black
import com.example.motsi.core.ui.theming.MotsiTheme
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.core.ui.designsystem.buttons.mainbutton.MainButton
import com.example.motsi.core.ui.designsystem.buttons.mainbutton.ButtonColorStyle
import com.example.motsi.feature.login.impl.presentation.LoginViewModel


@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel
) {

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                Image(
                    painter = painterResource(id = com.example.motsi.core.ui.R.drawable.ic_launcher_foreground),
                    contentDescription = "",
                    modifier = Modifier
                        .size(120.dp)
                )

                Text(
                    text = stringResource(com.example.motsi.core.ui.R.string.log_in_or_register),
                    textAlign = TextAlign.Center,
                    style = MotsiTheme.textAppearance.Body1.copy(
                        color = Tokens.TextPrimary.getColor()
                    )
                )

                MainButton(
                    text = stringResource(R.string.brand),
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {  },
                    style = ButtonColorStyle.BrandButton,
                    border = null
                )

                MainButton(
                    text = stringResource(R.string.inverse),
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {  },
                    style = ButtonColorStyle.InverseButton,
                    border = ButtonBorderStyle.InverseButtonBorder
                )
            }
        }
    }
}