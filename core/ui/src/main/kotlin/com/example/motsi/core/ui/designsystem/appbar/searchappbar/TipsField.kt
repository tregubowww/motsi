package com.example.motsi.core.ui.designsystem.appbar.searchappbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.theming.SubtitlePrimarySmall
import com.example.motsi.core.ui.theming.Title1PrimarySmall
import com.example.motsi.core.ui.theming.Tokens

@Composable
fun TipsField(
    modifier: Modifier = Modifier,
    onTipsFieldClicked: (String) -> Unit = {},
    iconTips: Int,
    tips: String,
    tipsCategory: String? = null,
    isNotLastItem: Boolean
) {
    Row(
        modifier = modifier
            .clickable(onClick = { onTipsFieldClicked(tips) })
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconTips),
            tint = Tokens.IconPrimary.getColor(),
            contentDescription = stringResource(R.string.icon_tips),
            modifier = Modifier.padding(start = 8.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Title1PrimarySmall(text = tips, maxLines = 2)
            if (tipsCategory != null) {
                SubtitlePrimarySmall(text = tipsCategory, maxLines = 1)
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_forward_14dp),
            tint = Tokens.IconPrimary.getColor(),
            contentDescription = stringResource(R.string.next),
        )
    }

    if (isNotLastItem) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 46.dp),
            color = Tokens.TextSecondary.getColor(),
            thickness = 1.dp
        )
    }
}