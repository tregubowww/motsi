package com.example.motsi.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun MainScreen() {
    var isSplashVisible by remember { mutableStateOf(true) }

    SplashScreen {
        isSplashVisible = false
    }

    if (!isSplashVisible) {
        Navigation()
    }
}

