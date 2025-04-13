package com.example.motsi.impl.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.motsi.impl.presentation.ActivityDetailsViewModel

@Composable
internal fun ActivityDetailsScreen(viewModel: ActivityDetailsViewModel) {
    Box(modifier = Modifier.fillMaxSize().background(Color.Blue))
}