package com.example.motsi.core.network.models.data

data class ErrorResponse(
    val messageText: String?,
    val title: String,
    val subTitle: String,
    val positiveButton: ErrorButton?,
    val neutralButton: ErrorButton?,
    val negativeButton: ErrorButton?,
) {
    data class ErrorButton(
        val title: String,
        val action: String
    )
}