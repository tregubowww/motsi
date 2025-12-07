package com.example.motsi.core.ui.designsystem.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.theming.Body3PrimaryInverse
import com.example.motsi.core.ui.theming.Tokens

@Composable
fun TipChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (enabled)
                    Tokens.BackgroundBrandChip.getColor()
                else
                    Tokens.BackgroundBrand.getColor().copy(alpha = 0.6f)
            )
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Body3PrimaryInverse(
            text = text,
            maxLines = 1
        )
    }
}