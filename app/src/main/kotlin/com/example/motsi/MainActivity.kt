package com.example.motsi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.ui.Modifier
import com.example.motsi.ui.compose.Navigation
import com.example.motsi.core.ui.theming.MotsiTheme

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val statusBarPadding = WindowInsets.statusBars.asPaddingValues()
            MotsiTheme {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .padding(top = statusBarPadding.calculateTopPadding())
                ) {
                    Navigation()
                }
            }
        }
    }
}