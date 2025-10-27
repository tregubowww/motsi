package com.example.motsi.core.ui.designsystem.snackbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.models.DataSnackbar
import com.example.motsi.core.ui.theming.Body2Secondary
import com.example.motsi.core.ui.theming.Body3Brand
import com.example.motsi.core.ui.theming.Tokens

@Composable
fun CustomSnackbarHost(
    modifier: Modifier = Modifier,
    hostState: SnackbarHostState,
    onAction: (() -> Unit)? = null,
) {
    SnackbarHost(hostState = hostState, modifier = modifier) { snackbarData ->
        val data = snackbarData.visuals as? DataSnackbar

        Snackbar(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(24.dp),
            containerColor = Tokens.BackgroundPrimary.getColor(),
            action = {
                if (data?.type == DataSnackbar.SnackbarType.Action && !data.actionLabel.isNullOrEmpty()) {
                    Body3Brand(
                        text = data.actionLabel,
                        modifier = Modifier.clickable {
                            onAction?.invoke()
                            snackbarData.performAction()
                        }
                    )
                }
            }
        ) {
            Body2Secondary(
                text = data?.message.orEmpty(),
                maxLines = 3
            )
        }
    }
}

