package com.example.motsi.feature.login.impl.presentation.view_model

import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.presentation.PasswordVisualTransformation
import com.example.motsi.feature.login.impl.interactor.FieldType
import com.example.motsi.feature.login.impl.interactor.RegisterInteractor
import com.example.motsi.feature.login.impl.interactor.ValidationError
import com.example.motsi.feature.login.impl.interactor.ValidationMessages
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class RegisterViewModel @Inject constructor(
    private val interactor: RegisterInteractor
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    private val errorMessages = mapOf(
        ValidationError.USERNAME_REQUIRED to ValidationMessages.USERNAME_REQUIRED,
        ValidationError.INVALID_USERNAME_FORMAT to ValidationMessages.INVALID_USERNAME_FORMAT,
        ValidationError.EMAIL_REQUIRED to ValidationMessages.EMAIL_REQUIRED,
        ValidationError.INVALID_EMAIL_FORMAT to ValidationMessages.INVALID_EMAIL_FORMAT,
        ValidationError.PASSWORD_REQUIRED to ValidationMessages.PASSWORD_REQUIRED,
        ValidationError.WEAK_PASSWORD to ValidationMessages.WEAK_PASSWORD,
        ValidationError.PASSWORD_REPEAT_REQUIRED to ValidationMessages.PASSWORD_REPEAT_REQUIRED,
        ValidationError.PASSWORDS_MISMATCH to ValidationMessages.PASSWORDS_MISMATCH
    )

    init {
        viewModelScope.launch {
            snapshotFlow { state.value.password1 }
                .collect { password ->
                    if (password.isNotEmpty()) {
                        updatePasswordCharVisibility(
                            lastCharIndex = password.length - 1,
                            passwordField = PasswordField.FIRST
                        )
                        delay(500)
                        updatePasswordCharVisibility(
                            lastCharVisible = false,
                            passwordField = PasswordField.FIRST
                        )
                    }
                }
        }

        viewModelScope.launch {
            snapshotFlow { state.value.password2 }
                .collect { password ->
                    if (password.isNotEmpty()) {
                        updatePasswordCharVisibility(
                            lastCharIndex = password.length - 1,
                            passwordField = PasswordField.SECOND
                        )
                        delay(500)
                        updatePasswordCharVisibility(
                            lastCharVisible = false,
                            passwordField = PasswordField.SECOND
                        )
                    }
                }
        }
    }

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

    private fun updatePasswordCharVisibility(
        lastCharVisible: Boolean? = null,
        lastCharIndex: Int? = null,
        passwordField: PasswordField
    ) {
        _state.update { current ->
            when (passwordField) {
                PasswordField.FIRST -> current.copy(
                    lastCharVisible1 = lastCharVisible ?: current.lastCharVisible1,
                    lastCharIndex1 = lastCharIndex ?: current.lastCharIndex1
                )
                PasswordField.SECOND -> current.copy(
                    lastCharVisible2 = lastCharVisible ?: current.lastCharVisible2,
                    lastCharIndex2 = lastCharIndex ?: current.lastCharIndex2
                )
            }
        }
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
                usernameError = errorMessages[errors[FieldType.USERNAME]],
                emailError = errorMessages[errors[FieldType.EMAIL]],
                password1Error = errorMessages[errors[FieldType.PASSWORD1]],
                password2Error = errorMessages[errors[FieldType.PASSWORD2]]
            )
        }
        return errors.values.all { it == null }
    }

    private enum class PasswordField { FIRST, SECOND }
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