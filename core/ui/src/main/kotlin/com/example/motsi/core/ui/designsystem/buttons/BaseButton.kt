package com.example.motsi.core.ui.designsystem.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.theming.ColorToken
import com.example.motsi.core.ui.theming.Body3PrimaryInverse

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    text: String,
    color: ColorToken,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize( minHeight = 50.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = color.getColor(),
            )
            .clickable(
                role = Role.Button,
                onClick = { onClick.invoke() }
            )
        ,
        contentAlignment = Alignment.Center
    ) {
        Body3PrimaryInverse( text = text)
    }
}