package com.example.motsi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.motsi.core.navigation.presentation.SharedSplashScreenViewModel
import com.example.motsi.core.navigation.presentation.compose.Navigation
import com.example.motsi.core.ui.theming.MotsiTheme

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        val api =  application.appComponent
        setContent {
            val sharedSplashScreenViewModel: SharedSplashScreenViewModel =  viewModel(
                viewModelStoreOwner =  LocalActivity.current as ComponentActivity,
                factory = api.viewModelFactory()
            )

            val isShowSplashScreen by sharedSplashScreenViewModel.isShowSplashScreen.collectAsState()
            splashScreen.setKeepOnScreenCondition {
                isShowSplashScreen
            }
            MotsiTheme {
                Navigation(
                    navEntrySet = api.navManager().featureNavEntrySet,
                    startDestination = api.navManager().startDestination,
                    itemsBottomNavBar = api.navManager().getNavBottomBarItem()
                )
            }
        }
    }
}