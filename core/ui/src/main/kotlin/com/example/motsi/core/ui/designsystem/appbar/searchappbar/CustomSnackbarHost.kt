package com.example.motsi.core.ui.designsystem.appbar.searchappbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.theming.Body2Secondary
import com.example.motsi.core.ui.theming.Body3Brand
import com.example.motsi.core.ui.theming.Tokens

@Composable
fun CustomSnackbarHost(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onActionClick: () -> Unit
) {
    SnackbarHost(
        modifier = modifier
            .padding(16.dp)
            .background(
                color = Tokens.BackgroundPrimary.getColor(),
                shape = RoundedCornerShape(24.dp)
            ),
        hostState = snackbarHostState,
    ) { data ->
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Body2Secondary(
                text = data.visuals.message,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )

            data.visuals.actionLabel?.let { actionLabel ->
                Body3Brand(
                    modifier = Modifier
                        .clickable { onActionClick(); data.performAction() }
                        .padding(horizontal = 16.dp)
                        .align(Alignment.End),
                    text = actionLabel
                )
            }
        }
    }
}