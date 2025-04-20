package com.example.motsi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.motsi.core.ui.theming.MotsiTheme
import com.example.motsi.ui.compose.Navigation

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().setKeepOnScreenCondition {
            false
        }
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)
        setContent {
            MotsiTheme {
                Navigation()
            }
        }
    }
}