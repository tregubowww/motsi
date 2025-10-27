package com.example.motsi.core.ui.designsystem.indicators

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.theming.Tokens

@Composable
fun ProgressIndicatorCircular(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier.size(48.dp),
        color = Tokens.IconPrimary.getColor(),
        strokeWidth = 3.dp
    )
}