package com.example.motsi.core.ui.designsystem.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.theming.Subtitle2PrimaryInverse

@Composable
fun Badge(modifier: Modifier = Modifier, color: Color, text: String, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .background(color, shape = RoundedCornerShape(percent = 50))
            .wrapContentSize(Alignment.Center)
            .clip(RoundedCornerShape(percent = 50))
            .clickable(
                role = Role.Button,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Subtitle2PrimaryInverse(text = text, modifier = Modifier.padding(4.dp))
    }
}