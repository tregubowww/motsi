package com.example.motsi.feature.login.impl.presentation.view_model

import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewModelScope
import com.example.motsi.core.common.models.data.ResultWrapper
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.common.presentation.BaseViewModel
import com.example.motsi.core.common.presentation.PasswordVisualTransformation
import com.example.motsi.core.common.presentation.utils.handleState
import com.example.motsi.core.network.models.domain.MotsiError
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

    // Состояние формы
    val state: StateFlow<RegisterState>
        get() = _state.asStateFlow()
    private val _state = MutableStateFlow(RegisterState())

    private var validationJobs = mutableMapOf<FieldType, Job>()


    fun updateUsername(username: String) {
        _state.update { it.copy(username = username) }
        validateWithDebounce(FieldType.USERNAME, username)
    }

    fun updateEmail(email: String) {
        _state.update { it.copy(email = email) }
        validateWithDebounce(FieldType.EMAIL, email)
    }

    fun updatePassword1(password: String) {
        _state.update { it.copy(password1 = password) }
        validateWithDebounce(FieldType.PASSWORD1, password)
        validatePasswordMatch()
    }

    fun updatePassword2(password: String) {
        _state.update { it.copy(password2 = password) }
        validatePasswordMatch()
    }

    fun togglePasswordVisibility1() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun togglePasswordVisibility2() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }


    private fun validateWithDebounce(field: FieldType, value: String) {
        validationJobs[field]?.cancel()
        validationJobs[field] = viewModelScope.launch {
            delay(1000)

            val result = when (field) {
                FieldType.USERNAME -> interactor.validateUsername(value)
                FieldType.EMAIL -> interactor.validateEmail(value)
                FieldType.PASSWORD1 -> interactor.validatePassword(value)
                FieldType.PASSWORD2 -> interactor.validatePasswordMatch(value)
            }

            handleValidationResult(field, result)
        }
    }

    // Проверка совпадения паролей. Исправить!
    private fun validatePasswordMatch() {
        viewModelScope.launch {
            val password1 = _state.value.password1
            val password2 = _state.value.password2

            if (password2.isNotEmpty()) {
                val result = interactor.validatePasswordMatch(password2)
                handleValidationResult(FieldType.PASSWORD2, result)
            }
        }
    }

    // Регистрация
    fun register(): Job = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        val result = interactor.register(
            username = _state.value.username,
            email = _state.value.email,
            password = _state.value.password1
        )

        _state.update { it.copy(isLoading = false) }
    }

    private fun handleValidationResult(field: FieldType, result: ResultWrapper<ValidationResultDomain, MotsiError>) {
        when (result) {
            is ResultWrapper.Success -> {
                // Для успешного результата проверяем isValid
                if (result.value.isValid) {
                    // Если валидация прошла - очищаем ошибку
                    _state.update { state ->
                        when (field) {
                            FieldType.USERNAME -> state.copy(usernameError = null)
                            FieldType.EMAIL -> state.copy(emailError = null)
                            FieldType.PASSWORD1 -> state.copy(password1Error = null)
                            FieldType.PASSWORD2 -> state.copy(password2Error = null)
                        }
                    }
                } else {
                    // Если есть ошибки - берём первую
                    val errorMessage = result.value.errors.firstOrNull()?.let {
                        when (it) {
                            is ValidationErrorDomain.UsernameError -> it.message
                            is ValidationErrorDomain.EmailError -> it.message
                            is ValidationErrorDomain.PasswordError -> it.message
                        }
                    }

                    _state.update { state ->
                        when (field) {
                            FieldType.USERNAME -> state.copy(usernameError = errorMessage)
                            FieldType.EMAIL -> state.copy(emailError = errorMessage)
                            FieldType.PASSWORD1 -> state.copy(password1Error = errorMessage)
                            FieldType.PASSWORD2 -> state.copy(password2Error = errorMessage)
                        }
                    }
                }
            }
            is ResultWrapper.Error -> {
                //Всегда приходит ошибка сети!!!
                // Обработка ошибки сети/сервера
                val errorMessage = when (result.error) {
                    is MotsiError.Network -> "Ошибка сети"
                    is MotsiError.Server -> "Ошибка сервера"
                    else -> "Неизвестная ошибка"
                }

                _state.update { state ->
                    when (field) {
                        FieldType.USERNAME -> state.copy(usernameError = errorMessage)
                        FieldType.EMAIL -> state.copy(emailError = errorMessage)
                        FieldType.PASSWORD1 -> state.copy(password1Error = errorMessage)
                        FieldType.PASSWORD2 -> state.copy(password2Error = errorMessage)
                    }
                }
            }
        }
    }

    override fun onInit() {
        viewModelScope.launch {
            //interactor.getRegisterScreen().handleState(_registerScreenState)
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
sealed class RegistrationResult {
    data class Success(val userId: String) : RegistrationResult()
    data class Error(val message: String) : RegistrationResult()
}

enum class FieldType { USERNAME, EMAIL, PASSWORD1, PASSWORD2 }



/** Старый код. Оставил на всякий. Удалить */

//
//internal class RegisterViewModel @Inject constructor(
//    private val interactor: LoginInteractor
//) : BaseViewModel() {
//
//    private val _state = MutableStateFlow(RegisterState())
//    val state: StateFlow<RegisterState> = _state
//
//    private var validationJobs = mutableMapOf<String, Job>()
//
//    private val validationState: StateFlow<LoginInteractor.ValidationState> =
//        interactor.observeValidationState()
//
//    fun validateUsername(username: String) {
//        validateWithDebounce("username") { interactor.validateUsername(username) }
//    }
//
//    fun validateEmail(email: String) {
//        validateWithDebounce("email") { interactor.validateEmail(email) }
//    }
//
//    fun validatePassword(password: String) {
//        validateWithDebounce("password") { interactor.validatePassword(password) }
//    }
//
//    private fun validateWithDebounce(
//        field: String,
//        validation: suspend () -> LoginInteractor.ValidationResult
//    ) {
//        validationJobs[field]?.cancel()
//        validationJobs[field] = viewModelScope.launch {
//            delay(500)
//            validation()
//        }
//    }
//
//    suspend fun register(): Boolean {
//        return when (interactor.register(
//            username = validationState.value.username ?: "",
//            email = validationState.value.email ?: "",
//            password = validationState.value.password ?: ""
//        )) {
//            LoginInteractor.RegistrationResult.Success -> true
//            is LoginInteractor.RegistrationResult.Error -> false
//        }
//    }
//
//    fun updateUsername(username: String) {
//        _state.value = _state.value.copy(username = username)
//        validateWithDebounce("username") { interactor.validateUsername(username) }
//    }
//
//    fun updateEmail(email: String) {
//        _state.value = _state.value.copy(email = email)
//        validateWithDebounce("email") { interactor.validateEmail(email) }
//    }
//
//    fun updatePassword1(password: String) {
//        _state.value = _state.value.copy(password1 = password)
//        validateWithDebounce("password1") { interactor.validatePassword(password) }
//        validatePasswordMatch()
//    }
//
//    fun updatePassword2(password: String) {
//        _state.value = _state.value.copy(password2 = password)
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
//    private fun validateWithDebounce(
//        field: String,
//        validation: suspend () -> String?
//    ) {
//        validationJobs[field]?.cancel()
//        validationJobs[field] = viewModelScope.launch {
//            delay(500)
//            val error = validation()
//            _state.value = when (field) {
//                "username" -> _state.value.copy(usernameError = error)
//                "email" -> _state.value.copy(emailError = error)
//                "password1" -> _state.value.copy(password1Error = error)
//                else -> _state.value
//            }
//        }
//    }
//
//    private fun validatePasswordMatch() {
//        val current = _state.value
//        if (current.password1.isNotEmpty() && current.password2.isNotEmpty()) {
//            _state.value = current.copy(
//                password2Error = if (current.password1 != current.password2) {
//                    "Пароли не совпадают"
//                } else {
//                    null
//                }
//            )
//        }
//    }
//
//    override fun onInit() {
//        viewModelScope.launch {
////            launch { interactor.getSearchScreen().handleState(_loadingScreenState) }
////            launch { interactor.getSearchList().handleState(_loadingSearchListState) }
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