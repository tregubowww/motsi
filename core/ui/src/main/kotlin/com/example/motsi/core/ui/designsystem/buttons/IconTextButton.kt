package com.example.motsi.core.ui.designsystem.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.theming.Body3Primary
import com.example.motsi.core.ui.theming.Tokens

@Composable
fun IconTextButton(
    modifier: Modifier,
    text: String,
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
            )
            .clickable { onClick() }
            .background(
                color = Tokens.Background.getColor(),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)

    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = Tokens.IconPrimary.getColor()
        )

        Body3Primary(
            modifier = Modifier.padding(start = 8.dp),
            text = text
        )
    }
}