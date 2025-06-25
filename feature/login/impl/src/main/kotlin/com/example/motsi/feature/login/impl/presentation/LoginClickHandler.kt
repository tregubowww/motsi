package com.example.motsi.feature.login.impl.presentation
import androidx.navigation.NavHostController
import com.example.motsi.feature.login.impl.models.presentation.EmailCodeScreenDestination
import com.example.motsi.feature.login.impl.models.presentation.AuthScreenDestination
import com.example.motsi.feature.login.impl.models.presentation.RegisterScreenDestination
import javax.inject.Inject

internal class LoginClickHandler @Inject constructor() {

    fun onInverseClicked(navController: NavHostController) {
        navController.navigate(RegisterScreenDestination)
    }

    fun onBrandClicked(navController: NavHostController) {
        navController.navigate(AuthScreenDestination)
    }

    fun onBackPressed(navController: NavHostController) {
        navController.popBackStack()
    }

    fun onRegisterSuccess(navController: NavHostController) {
        navController.navigate(EmailCodeScreenDestination)
    }
}
