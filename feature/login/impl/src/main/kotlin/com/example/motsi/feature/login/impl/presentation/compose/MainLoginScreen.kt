package com.example.motsi.feature.login.impl.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.designsystem.buttons.mainbutton.DoActionButton
import com.example.motsi.core.ui.designsystem.buttons.mainbutton.ButtonStyle
import com.example.motsi.core.ui.theming.Body1Primary
import com.example.motsi.core.ui.theming.Headline1Primary
import com.example.motsi.core.ui.theming.Title1Primary
import com.example.motsi.core.ui.theming.Tokens


@Composable
internal fun MainLoginScreen(
    onInverseClicked: () -> Unit,
    onBrandClicked: () -> Unit
) {

    Scaffold(
        containerColor = Tokens.Background.getColor(),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 41.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "",
                modifier = Modifier
                    .size(200.dp)
            )

            Title1Primary(
                text = stringResource(com.example.motsi.feature.login.impl.R.string.login_or_register)
            )

            DoActionButton(
                text = stringResource(com.example.motsi.feature.login.impl.R.string.enter),
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { onBrandClicked() },
                style = ButtonStyle.BrandButton
            )

            DoActionButton(
                text = stringResource(com.example.motsi.feature.login.impl.R.string.register),
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { onInverseClicked() },
                style = ButtonStyle.InverseButton
            )
        }
    }
}