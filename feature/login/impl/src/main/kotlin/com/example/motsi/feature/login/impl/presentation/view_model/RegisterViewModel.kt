package com.example.motsi.feature.login.impl.presentation.view_model

import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import com.example.motsi.core.common.presentation.PasswordVisualTransformation
import com.example.motsi.feature.login.impl.interactor.FieldType
import com.example.motsi.feature.login.impl.interactor.RegisterInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

internal class RegisterViewModel @Inject constructor(
    private val interactor: RegisterInteractor
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    fun updateUsername(username: String) {
        _state.update { it.copy(username = username) }
    }

    fun updateEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun updatePassword1(password: String) {
        _state.update { it.copy(password1 = password) }
    }

    fun updatePassword2(password: String) {
        _state.update { it.copy(password2 = password) }
    }

    fun togglePasswordVisibility() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun validateAndRegister(): Boolean {
        val current = _state.value
        val errors = interactor.validateFields(
            current.username,
            current.email,
            current.password1,
            current.password2
        )

        _state.update {
            it.copy(
                usernameError = errors[FieldType.USERNAME],
                emailError = errors[FieldType.EMAIL],
                password1Error = errors[FieldType.PASSWORD1],
                password2Error = errors[FieldType.PASSWORD2],
                error = null
            )
        }

        return errors.values.all { it == null }
    }
}

data class RegisterState(
    val username: String = "",
    val email: String = "",
    val password1: String = "",
    val password2: String = "",
    val usernameError: String? = null,
    val emailError: String? = null,
    val password1Error: String? = null,
    val password2Error: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isPasswordVisible: Boolean = false,
    val lastCharVisible1: Boolean = false,
    val lastCharIndex1: Int = -1,
    val lastCharVisible2: Boolean = false,
    val lastCharIndex2: Int = -1
) {

    val passwordTransformation1: VisualTransformation
        get() = if (isPasswordVisible) VisualTransformation.None
        else PasswordVisualTransformation(lastCharVisible1, lastCharIndex1)

    val passwordTransformation2: VisualTransformation
        get() = if (isPasswordVisible) VisualTransformation.None
        else PasswordVisualTransformation(lastCharVisible2, lastCharIndex2)
}