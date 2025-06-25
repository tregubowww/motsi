package com.example.motsi.core.ui.designsystem.buttons.mainbutton

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.theming.Body1Primary
import com.example.motsi.core.ui.theming.MotsiTheme
import com.example.motsi.core.ui.theming.Tokens

@Composable
fun DoActionButton(
    text: String,
    style: ButtonStyle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    paddings: PaddingValues? = null
) = DoActionButton(
    text = text,
    onClick = onClick,
    modifier = modifier,
    isEnabled = isEnabled,
    backgroundColor = style.backgroundColor.getColor(),
    textColor = style.textColor.getColor(),
    textStyle = MotsiTheme.textAppearance.Body1,
    borderColor = style.borderColor?.getColor(),
    borderWidth = style.borderWidth,
)


enum class ButtonStyle(
    val backgroundColor: Tokens,
    val textColor: Tokens,
    val borderColor: Tokens?,
    val borderWidth: Dp?,
) {
    BrandButton(
        backgroundColor = Tokens.ButtonPrimary,
        textColor = Tokens.InverseTextPrimary,
        borderColor = null,
        borderWidth = null,
    ),

    InverseButton(
        backgroundColor = Tokens.InverseButtonPrimary,
        textColor = Tokens.TextPrimary,
        borderColor = Tokens.ButtonPrimary,
        borderWidth = 1.dp,
    ),
}

@Composable
internal fun DoActionButton(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color,
    textColor: Color,
    textStyle: TextStyle,
    modifier: Modifier,
    isEnabled: Boolean = true,
    borderColor: Color? = null,
    borderWidth: Dp? = null,
    content: @Composable (RowScope.() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val shape = MotsiTheme.roundedShapes.doActionButtonRoundedShape
    val buttonModifier = if (borderColor != null && borderWidth != null) {
        modifier.then(
            Modifier.border(
                width = borderWidth,
                color = borderColor,
                shape = shape
            )
        )
    } else {
        modifier
    }

    Button(
        onClick = onClick,
        enabled = isEnabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        interactionSource = interactionSource,
        modifier = buttonModifier
    ) {
        if (content != null) {
            content()
        } else {
            Body1Primary(
                text = text,
                color = textColor,
                maxLines = 1
            )
        }
    }
}