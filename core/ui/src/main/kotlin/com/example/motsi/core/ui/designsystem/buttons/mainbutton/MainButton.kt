package com.example.motsi.core.ui.designsystem.buttons.mainbutton

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.theming.MotsiTheme
import com.example.motsi.core.ui.theming.Tokens

@Composable
fun MainButton(
    text: String,
    style: ButtonColorStyle,
    border: ButtonBorderStyle?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    MainButtonInternal(
        text = text,
        style = style,
        border = border,
        onClick = onClick,
        modifier= modifier,
        isEnabled = isEnabled
    )
}

@Composable
internal fun MainButtonInternal(
    text: String,
    style: ButtonColorStyle,
    border: ButtonBorderStyle?,
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
    borderColor = border?.borderColor?.getColor(),
    borderWidth = border?.borderWidth,
    shape = MotsiTheme.roundedShapes.small
)


enum class ButtonColorStyle(
    val backgroundColor: Tokens,
    val textColor: Tokens,
) {
    BrandButton(
        backgroundColor = Tokens.Brand,
        textColor = Tokens.TextPrimary,
    ),

    InverseButton(
        backgroundColor = Tokens.Background,
        textColor = Tokens.Brand,
    ),
}


enum class ButtonBorderStyle(
    val borderColor: Tokens,
    val borderWidth: Dp,
) {
    InverseButtonBorder(
        borderColor = Tokens.TextPrimary,
        borderWidth = 1.dp,
    )
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
    shape: RoundedCornerShape = MotsiTheme.roundedShapes.small,
    content: @Composable (RowScope.() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val buttonModifier = if (borderColor != null && borderWidth != null && borderWidth > 0.dp) {
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
            containerColor = backgroundColor
        ),
        interactionSource = interactionSource,
        modifier = buttonModifier
    ) {
        if (content != null) {
            content()
        } else {
            Text(
                text = text,
                style = textStyle,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}