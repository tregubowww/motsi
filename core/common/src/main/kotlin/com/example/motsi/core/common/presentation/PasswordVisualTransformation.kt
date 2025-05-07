package com.example.motsi.core.common.presentation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation


class PasswordVisualTransformation(
    private val showLastChar: Boolean,
    private val lastCharIndex: Int
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return if (showLastChar && text.isNotEmpty() && lastCharIndex in text.indices) {
            val transformed = text.mapIndexed { index, char ->
                if (index == lastCharIndex) char else '•'
            }.joinToString("")
            TransformedText(AnnotatedString(transformed), OffsetMapping.Identity)
        } else {
            PasswordVisualTransformation().filter(text)
        }
    }
}