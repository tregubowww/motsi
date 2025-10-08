package com.example.motsi.core.ui.designsystem.fields

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.theming.Subtitle2Primary
import com.example.motsi.core.ui.theming.Title2Primary
import com.example.motsi.core.ui.theming.Tokens

@Composable
fun BaseIconTextField(
    modifier: Modifier = Modifier,
    onFieldClick: () -> Unit = {},
    @DrawableRes icon: Int,
    title: String,
    subtitle: String?,
    isDividerVisible: Boolean
) {
    Row(
        modifier = modifier
            .clickable(onClick = { onFieldClick() })
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            tint = Tokens.IconPrimary.getColor(),
            contentDescription = null,
            modifier = Modifier.padding(start = 8.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Title2Primary(text = title, maxLines = 2)
            if (subtitle != null) {
                Subtitle2Primary(text = subtitle, maxLines = 1)
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_forward_14dp),
            tint = Tokens.IconPrimary.getColor(),
            contentDescription = null,
        )
    }

    if (isDividerVisible) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 16.dp),
            color = Tokens.TextSecondary.getColor(),
        )
    }
}