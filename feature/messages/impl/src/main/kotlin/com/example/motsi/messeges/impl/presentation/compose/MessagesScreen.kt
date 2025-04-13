package com.example.motsi.messeges.impl.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.motsi.messeges.impl.presentation.MessagesViewModel

@Composable
internal fun MessagesScreen(
    viewModel: MessagesViewModel,
    bottomNavBar: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier,
        bottomBar = bottomNavBar
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
        ) {
        }
    }
}