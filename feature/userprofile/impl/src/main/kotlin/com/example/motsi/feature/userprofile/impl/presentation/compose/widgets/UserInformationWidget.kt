package com.example.motsi.feature.userprofile.impl.presentation.compose.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.designsystem.baseline.HorizontalLine
import com.example.motsi.core.ui.theming.Body1Primary
import com.example.motsi.core.ui.theming.Body3Brand
import com.example.motsi.core.ui.theming.Body3Primary
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.feature.userprofile.impl.models.domain.UserProfileScreenModel

@Composable
internal fun UserInformationWidget(
    userInformation: UserProfileScreenModel.UserInformation
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = 16.dp)
            .background(color = Tokens.Background.getColor(), shape = RoundedCornerShape(12.dp))
    ) {
        Body1Primary(
            text = userInformation.phoneNumberTitle,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp)
        )
        Body3Brand(
            modifier = Modifier.padding(bottom = 8.dp, start = 16.dp),
            text = userInformation.phoneNumberValue
        )
        HorizontalLine(modifier = Modifier.padding(start = 16.dp))

        Body1Primary(
            text = userInformation.usernameTitle,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp)
        )
        Body3Brand(
            modifier = Modifier.padding(bottom = 8.dp, start = 16.dp),
            text = userInformation.usernameValue
        )
        HorizontalLine(modifier = Modifier.padding(start = 16.dp))

        Body1Primary(
            text = userInformation.birthdayTitle,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp)
        )
        Body3Primary(
            modifier = Modifier.padding(bottom = 8.dp, start = 16.dp),
            text = userInformation.birthdayValue
        )
        HorizontalLine(modifier = Modifier.padding(start = 16.dp))

        Body1Primary(
            text = userInformation.informationTitle,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp)
        )
        Body3Brand(
            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp),
            text = userInformation.informationValue
        )
    }
}