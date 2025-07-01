package com.example.motsi.feature.login.impl.presentation.view_model

import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.PasswordVisualTransformation
import com.example.motsi.core.common.presentation.utils.handleState
import com.example.motsi.core.network.models.domain.MotsiError
import com.example.motsi.feature.login.impl.data.repository.FieldType
import com.example.motsi.feature.login.impl.di.LoginHolder
import com.example.motsi.feature.login.impl.domain.interactor.LoginInteractor
import com.example.motsi.feature.login.impl.models.domain.RegisterScreenDomainModel
import com.example.motsi.feature.login.impl.models.domain.ValidationErrorDomain
import com.example.motsi.feature.login.impl.models.domain.ValidationResultDomain
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class RegisterViewModel @Inject constructor(
    private val interactor: LoginInteractor
) : BaseViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    private val _isFormValid = MutableStateFlow(false)
    val isFormValid: StateFlow<Boolean> = _isFormValid

    private var validationJobs = mutableMapOf<FieldType, Job>()

    fun updateUsername(username: String) {
        _state.update { it.copy(username = username, usernameTouched = true) }
        validateWithDebounce(FieldType.USERNAME, username)
    }

    fun updateEmail(email: String) {
        _state.update { it.copy(email = email, emailTouched = true) }
        validateWithDebounce(FieldType.EMAIL, email)
    }

    fun updatePassword1(password: String) {
        val lastCharVisible = password.length > _state.value.password1.length &&
                !_state.value.isPassword1Visible
        _state.update {
            it.copy(
                password1 = password,
                password1Touched = true,
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

        validateWithDebounce(FieldType.PASSWORD1, password)
//        validatePasswordMatch()
    }

    fun updatePassword2(password: String) {
        val lastCharVisible = password.length > _state.value.password2.length &&
                !_state.value.isPassword2Visible
        _state.update {
            it.copy(
                password2 = password,
                password2Touched = true,
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

        validatePasswordMatch()
    }



    fun register(onResult: (Boolean) -> Unit) {
        if (!_isFormValid.value) {
            onResult(false)
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = try {
                interactor.registerUser(
                    username = _state.value.username,
                    email = _state.value.email,
                    password = _state.value.password1
                )
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Ошибка сети: ${e.message}"
                    )
                }
                onResult(false)
                return@launch
            }

            _state.update {
                it.copy(
                    isLoading = false,
                    error = if (!result) "Ошибка регистрации" else null
                )
            }

            onResult(result)
        }
    }

    private fun updateFormValidity(errors: Map<FieldType, String?>) {
        val isValid = errors.values.all { it == null } &&
                _state.value.usernameTouched &&
                _state.value.emailTouched &&
                _state.value.password1Touched &&
                _state.value.password2Touched

        _isFormValid.value = isValid
    }

    private fun validateWithDebounce(field: FieldType, value: String) {
        validationJobs[field]?.cancel()
        validationJobs[field] = viewModelScope.launch {
            delay(800)

            val errors = interactor.validateFields(
                username = if (field == FieldType.USERNAME) value else _state.value.username,
                email = if (field == FieldType.EMAIL) value else _state.value.email,
                password1 = if (field == FieldType.PASSWORD1) value else _state.value.password1,
                password2 = _state.value.password2
            )

            _state.update { current ->
                current.copy(
                    usernameError = if (current.usernameTouched) errors[FieldType.USERNAME] else null,
                    emailError = if (current.emailTouched) errors[FieldType.EMAIL] else null,
                    password1Error = if (current.password1Touched) errors[FieldType.PASSWORD1] else null,
                    password2Error = if (current.password2Touched) errors[FieldType.PASSWORD2] else null
                )
            }
            updateFormValidity(errors)
        }
    }

    private fun validatePasswordMatch() {
        viewModelScope.launch {
            val errors = interactor.validateFields(
                username = _state.value.username,
                email = _state.value.email,
                password1 = _state.value.password1,
                password2 = _state.value.password2
            )

            _state.update { current ->
                current.copy(
                    password2Error = if (current.password2Touched) errors[FieldType.PASSWORD2] else null
                )
            }
            updateFormValidity(errors)
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
//            launch { interactor.getSearchScreen().handleState(_loadingScreenState) }
//            launch { interactor.getSearchList().handleState(_loadingSearchListState) }
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
    val isLoading: Boolean = false,
    val error: String? = null,
    val isPassword1Visible: Boolean = false,
    val isPassword2Visible: Boolean = false,
    val lastCharVisible1: Boolean = false,
    val lastCharIndex1: Int = -1,
    val lastCharVisible2: Boolean = false,
    val lastCharIndex2: Int = -1,

    val usernameTouched: Boolean = false,
    val emailTouched: Boolean = false,
    val password1Touched: Boolean = false,
    val password2Touched: Boolean = false
) {

    val passwordTransformation1: VisualTransformation
        get() = if (isPassword1Visible) VisualTransformation.None
        else PasswordVisualTransformation(lastCharVisible1, lastCharIndex1)

    val passwordTransformation2: VisualTransformation
        get() = if (isPassword2Visible) VisualTransformation.None
        else PasswordVisualTransformation(lastCharVisible2, lastCharIndex2)
}


/** Новый кривой код. Оставил на всякий. Удалить */
//internal class RegisterViewModel @Inject constructor(
//    private val interactor: LoginInteractor
//) : BaseViewModel() {
//
//    // Состояние формы
//    val state: StateFlow<RegisterState>
//        get() = _state.asStateFlow()
//    private val _state = MutableStateFlow(RegisterState())
//
//    private var validationJobs = mutableMapOf<FieldType, Job>()
//
//
//    fun updateUsername(username: String) {
//        _state.update { it.copy(username = username) }
//        validateWithDebounce(FieldType.USERNAME, username)
//    }
//
//    fun updateEmail(email: String) {
//        _state.update { it.copy(email = email) }
//        validateWithDebounce(FieldType.EMAIL, email)
//    }
//
//    fun updatePassword1(password: String) {
//        _state.update { it.copy(password1 = password) }
//        validateWithDebounce(FieldType.PASSWORD1, password)
//        validatePasswordMatch()
//    }
//
//    fun updatePassword2(password: String) {
//        _state.update { it.copy(password2 = password) }
//        validatePasswordMatch()
//    }
//
//    fun togglePasswordVisibility1() {
//        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
//    }
//
//    fun togglePasswordVisibility2() {
//        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
//    }
//
//
//    private fun validateWithDebounce(field: FieldType, value: String) {
//        validationJobs[field]?.cancel()
//        validationJobs[field] = viewModelScope.launch {
//            delay(1000)
//
//            val result = when (field) {
//                FieldType.USERNAME -> interactor.validateUsername(value)
//                FieldType.EMAIL -> interactor.validateEmail(value)
//                FieldType.PASSWORD1 -> interactor.validatePassword(value)
//                FieldType.PASSWORD2 -> interactor.validatePasswordMatch(value)
//            }
//
//            handleValidationResult(field, result)
//        }
//    }
//
//    // Проверка совпадения паролей. Исправить!
//    private fun validatePasswordMatch() {
//        viewModelScope.launch {
//            val password1 = _state.value.password1
//            val password2 = _state.value.password2
//
//            if (password2.isNotEmpty()) {
//                val result = interactor.validatePasswordMatch(password2)
//                handleValidationResult(FieldType.PASSWORD2, result)
//            }
//        }
//    }
//
//    // Регистрация
//    fun register(): Job = viewModelScope.launch {
//        _state.update { it.copy(isLoading = true) }
//
//        val result = interactor.register(
//            username = _state.value.username,
//            email = _state.value.email,
//            password = _state.value.password1
//        )
//
//        _state.update { it.copy(isLoading = false) }
//    }
//
//    private fun handleValidationResult(field: FieldType, result: ResultWrapper<ValidationResultDomain, MotsiError>) {
//        when (result) {
//            is ResultWrapper.Success -> {
//                // Для успешного результата проверяем isValid
//                if (result.value.isValid) {
//                    // Если валидация прошла - очищаем ошибку
//                    _state.update { state ->
//                        when (field) {
//                            FieldType.USERNAME -> state.copy(usernameError = null)
//                            FieldType.EMAIL -> state.copy(emailError = null)
//                            FieldType.PASSWORD1 -> state.copy(password1Error = null)
//                            FieldType.PASSWORD2 -> state.copy(password2Error = null)
//                        }
//                    }
//                } else {
//                    // Если есть ошибки - берём первую
//                    val errorMessage = result.value.errors.firstOrNull()?.let {
//                        when (it) {
//                            is ValidationErrorDomain.UsernameError -> it.message
//                            is ValidationErrorDomain.EmailError -> it.message
//                            is ValidationErrorDomain.PasswordError -> it.message
//                        }
//                    }
//
//                    _state.update { state ->
//                        when (field) {
//                            FieldType.USERNAME -> state.copy(usernameError = errorMessage)
//                            FieldType.EMAIL -> state.copy(emailError = errorMessage)
//                            FieldType.PASSWORD1 -> state.copy(password1Error = errorMessage)
//                            FieldType.PASSWORD2 -> state.copy(password2Error = errorMessage)
//                        }
//                    }
//                }
//            }
//            is ResultWrapper.Error -> {
//                //Всегда приходит ошибка сети!!!
//                // Обработка ошибки сети/сервера
//                val errorMessage = when (result.error) {
//                    is MotsiError.Network -> "Ошибка сети"
//                    is MotsiError.Server -> "Ошибка сервера"
//                    else -> "Неизвестная ошибка"
//                }
//
//                _state.update { state ->
//                    when (field) {
//                        FieldType.USERNAME -> state.copy(usernameError = errorMessage)
//                        FieldType.EMAIL -> state.copy(emailError = errorMessage)
//                        FieldType.PASSWORD1 -> state.copy(password1Error = errorMessage)
//                        FieldType.PASSWORD2 -> state.copy(password2Error = errorMessage)
//                    }
//                }
//            }
//        }
//    }
//
//    override fun onInit() {
//        viewModelScope.launch {
//            //interactor.getRegisterScreen().handleState(_registerScreenState)
//        }
//    }
//
//    override fun onRelease() {
//        LoginHolder.release()
//    }
//}
//
//data class RegisterState(
//    val username: String = "",
//    val email: String = "",
//    val password1: String = "",
//    val password2: String = "",
//    val usernameError: String? = null,
//    val emailError: String? = null,
//    val password1Error: String? = null,
//    val password2Error: String? = null,
//    val isLoading: Boolean = false,
//    val error: String? = null,
//    val isPasswordVisible: Boolean = false,
//    val lastCharVisible1: Boolean = false,
//    val lastCharIndex1: Int = -1,
//    val lastCharVisible2: Boolean = false,
//    val lastCharIndex2: Int = -1
//) {
//
//    val passwordTransformation1: VisualTransformation
//        get() = if (isPasswordVisible) VisualTransformation.None
//        else PasswordVisualTransformation(lastCharVisible1, lastCharIndex1)
//
//    val passwordTransformation2: VisualTransformation
//        get() = if (isPasswordVisible) VisualTransformation.None
//        else PasswordVisualTransformation(lastCharVisible2, lastCharIndex2)
//}
//sealed class RegistrationResult {
//    data class Success(val userId: String) : RegistrationResult()
//    data class Error(val message: String) : RegistrationResult()
//}
//
//enum class FieldType { USERNAME, EMAIL, PASSWORD1, PASSWORD2 }

