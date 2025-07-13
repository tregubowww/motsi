package com.example.motsi.feature.login.impl.presentation.view_model

import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.PasswordVisualTransformation
import com.example.motsi.feature.login.impl.di.LoginHolder
import com.example.motsi.feature.login.impl.domain.interactor.LoginInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class RegisterViewModel @Inject constructor(
    private val interactor: LoginInteractor
) : BaseViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    private var validationUsernameJob: Job? = null
    private var validationEmailJob: Job? = null
    private var validationPassword1Job: Job? = null
    private var validationPassword2Job: Job? = null

    fun updateUsername(username: String) {
        _state.update { it.copy(username = username) }
        validationUsernameJob?.cancel()
        validationUsernameJob = viewModelScope.launch {
            delay(1000)
            _state.update { it.copy(usernameError = interactor.validateUsername(username)) }
        }
    }

    fun updateEmail(email: String) {
        _state.update { it.copy(email = email) }
        validationEmailJob?.cancel()
        validationEmailJob = viewModelScope.launch {
            delay(1000)
            _state.update { it.copy(emailError = interactor.validateEmail(email)) }
        }
    }

    fun updatePassword1(password: String) {
        val lastCharVisible = password.length > _state.value.password1.length &&
                !_state.value.isPassword1Visible
        _state.update {
            it.copy(
                password1 = password,
                lastCharVisible1 = lastCharVisible,
                lastCharIndex1 = password.length - 1
            )
        }
        if (lastCharVisible) {
            viewModelScope.launch {
                delay(500)
                _state.update { current ->
                    current.copy(lastCharVisible1 = false)
                }
            }
        }
        validationPassword1Job?.cancel()
        validationPassword1Job = viewModelScope.launch {
            delay(1000)
            _state.update { it.copy(password1Error = interactor.validatePassword(password)) }
        }
    }

    fun updatePassword2(password: String) {
        val lastCharVisible = password.length > _state.value.password2.length &&
                !_state.value.isPassword2Visible
        _state.update {
            it.copy(
                password2 = password,
                lastCharVisible2 = lastCharVisible,
                lastCharIndex2 = password.length - 1
            )
        }
        if (lastCharVisible) {
            viewModelScope.launch {
                delay(500)
                _state.update { current ->
                    current.copy(lastCharVisible2 = false)
                }
            }
        }
        validationPassword2Job?.cancel()
        validationPassword2Job = viewModelScope.launch {
            delay(1000)
            _state.update { it.copy(password2Error = interactor.validatePasswordMatch(state.value.password1,password)) }
        }
    }


    fun validAndRegister(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            onResult(false)
            _state.update { it.copy(usernameError = interactor.validateUsername(state.value.username)) }
            _state.update { it.copy(emailError = interactor.validateEmail(state.value.email)) }
            _state.update { it.copy(password1Error = interactor.validatePassword(state.value.password1)) }
            _state.update { it.copy(password2Error = interactor.validatePasswordMatch(state.value.password1, state.value.password2)) }
            if (
                interactor.validateUsername(state.value.username) == null &&
                interactor.validateEmail(state.value.email) == null &&
                interactor.validatePassword(state.value.password1) == null &&
                interactor.validatePasswordMatch(state.value.password1, state.value.password2) == null
            ){

                interactor.registerUser(
                    username = _state.value.username,
                    email = _state.value.email,
                    password = _state.value.password1
                )
                onResult(true)
            }
        }
    }

    fun togglePasswordVisibility1() {
        _state.update {
            it.copy(
                isPassword1Visible = !it.isPassword1Visible,
                lastCharVisible1 = false
            )
        }
    }

    fun togglePasswordVisibility2() {
        _state.update {
            it.copy(
                isPassword2Visible = !it.isPassword2Visible,
                lastCharVisible2 = false
            )
        }
    }

    override fun onInit() {
        viewModelScope.launch {
        }
    }

    override fun onRelease() {
        LoginHolder.release()
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

    val isPassword1Visible: Boolean = false,
    val isPassword2Visible: Boolean = false,

    val lastCharVisible1: Boolean = false,
    val lastCharIndex1: Int = -1,
    val lastCharVisible2: Boolean = false,
    val lastCharIndex2: Int = -1,
) {

    val passwordTransformation1: VisualTransformation
        get() = if (isPassword1Visible) VisualTransformation.None
        else PasswordVisualTransformation(lastCharVisible1, lastCharIndex1)

    val passwordTransformation2: VisualTransformation
        get() = if (isPassword2Visible) VisualTransformation.None
        else PasswordVisualTransformation(lastCharVisible2, lastCharIndex2)
}