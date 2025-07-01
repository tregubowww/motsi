package com.example.motsi.feature.login.impl.validator

import com.example.motsi.feature.login.impl.data.networkservice.LoginRemoteDataSource
import com.example.motsi.feature.login.impl.models.data.RegisterRequest
import com.example.motsi.feature.login.impl.models.data.RegisterResponse
import com.example.motsi.feature.login.impl.models.data.RegisterScreenDataModel
import com.example.motsi.feature.login.impl.models.data.ValidationRequest
import com.example.motsi.feature.login.impl.models.data.ValidationResponse
import retrofit2.Response
import javax.inject.Inject
import kotlin.random.Random


class MockBackendValidator @Inject constructor() : LoginRemoteDataSource {

    private var lastPassword1: String? = null

    override suspend fun getRegisterScreen(): Response<RegisterScreenDataModel> {
        return Response.success(
            RegisterScreenDataModel(
                title = "Регистрация",
                fields = listOf(
                    RegisterScreenDataModel.RegisterFieldData(
                        type = "username",
                        label = "Имя пользователя",
                        hint = "Введите имя пользователя",
                        validationRules = listOf(
                            RegisterScreenDataModel.ValidationRuleData(
                                pattern = "^[a-zA-Z0-9_.-]{3,30}$",
                                errorMessage = "Допустимы буквы, цифры и символы _.- (3-30 символов)"
                            )
                        )
                    ),
                    RegisterScreenDataModel.RegisterFieldData(
                        type = "email",
                        label = "Email",
                        hint = "Введите ваш email",
                        validationRules = listOf(
                            RegisterScreenDataModel.ValidationRuleData(
                                pattern = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$",
                                errorMessage = "Введите корректный email"
                            )
                        )
                    ),
                    RegisterScreenDataModel.RegisterFieldData(
                        type = "password",
                        label = "Пароль",
                        hint = "Создайте пароль",
                        validationRules = listOf(
                            RegisterScreenDataModel.ValidationRuleData(
                                pattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%^&*()_+]).{8,}$",
                                errorMessage = "Пароль должен содержать не менее 8 символов, заглавную букву, цифру и спецсимвол"
                            )
                        )
                    )
                )
            )
        )
    }

    override suspend fun validateUsername(request: ValidationRequest): Response<ValidationResponse> {
        val errors = mutableListOf<ValidationResponse.ValidationErrorData>().apply {
            if (request.value.length < 3) add(ValidationResponse.ValidationErrorData(
                code = "username_too_short",
                message = "Минимум 3 символа"
            ))
            if (request.value.length > 30) add(ValidationResponse.ValidationErrorData(
                code = "username_too_long",
                message = "Максимум 30 символов"
            ))
            if (request.value.contains(" ")) add(ValidationResponse.ValidationErrorData(
                code = "username_spaces",
                message = "Пробелы запрещены"
            ))
            if (!Regex("^[a-zA-Z0-9_.-]*$").matches(request.value)) {
                add(ValidationResponse.ValidationErrorData(
                    code = "username_invalid",
                    message = "Только буквы, цифры и _.-"
                ))
            }
        }
        return Response.success(ValidationResponse(
            isValid = errors.isEmpty(),
            errors = errors
        ))
    }

    override suspend fun validateEmail(request: ValidationRequest): Response<ValidationResponse> {
        val errors = mutableListOf<ValidationResponse.ValidationErrorData>().apply {
            if (!Regex("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$").matches(request.value)) {
                add(ValidationResponse.ValidationErrorData(
                    code = "email_invalid",
                    message = "Некорректный email"
                ))
            }
        }
        return Response.success(ValidationResponse(
            isValid = errors.isEmpty(),
            errors = errors
        ))
    }

    override suspend fun validatePassword(request: ValidationRequest): Response<ValidationResponse> {
        lastPassword1 = request.value // Сохраняем первый пароль для последующей проверки
        val password = request.value
        val errors = mutableListOf<ValidationResponse.ValidationErrorData>().apply {
            if (password.length < 8) add(ValidationResponse.ValidationErrorData(
                code = "password_short",
                message = "Минимум 8 символов"
            ))
            if (!password.any { it.isUpperCase() }) add(ValidationResponse.ValidationErrorData(
                code = "password_no_upper",
                message = "Нужна заглавная буква"
            ))
            if (!password.any { it.isDigit() }) add(ValidationResponse.ValidationErrorData(
                code = "password_no_digit",
                message = "Нужна цифра"
            ))
            if (!password.any { it in "!@#$%^&*()_+" }) add(ValidationResponse.ValidationErrorData(
                code = "password_no_special",
                message = "Нужен спецсимвол"
            ))
        }
        return Response.success(ValidationResponse(
            isValid = errors.isEmpty(),
            errors = errors
        ))
    }

    override suspend fun validatePasswordMath(request: ValidationRequest): Response<ValidationResponse> {
        val errors = mutableListOf<ValidationResponse.ValidationErrorData>().apply {
            if (lastPassword1 == null) {
                add(ValidationResponse.ValidationErrorData(
                    code = "password_first_not_entered",
                    message = "Введите первый пароль"
                ))
            } else if (request.value != lastPassword1) {
                add(ValidationResponse.ValidationErrorData(
                    code = "passwords_not_match",
                    message = "Пароли не совпадают"
                ))
            }
        }
        return Response.success(ValidationResponse(
            isValid = errors.isEmpty(),
            errors = errors
        ))
    }

    override suspend fun registerUser(request: RegisterRequest): Response<RegisterResponse> {
        // Проверяем все поля перед регистрацией
        val usernameValidation = validateUsername(ValidationRequest(request.username)).body()
        val emailValidation = validateEmail(ValidationRequest(request.email)).body()
        val passwordValidation = validatePassword(ValidationRequest(request.password)).body()
        val passwordMatchValidation = validatePasswordMath(ValidationRequest(request.password)).body()

        val allValid = listOf(
            usernameValidation,
            emailValidation,
            passwordValidation,
            passwordMatchValidation
        ).all { it?.isValid == true }

        return if (allValid) {
            Response.success(RegisterResponse(
                success = true,
                userId = "user-${Random.nextInt(10000, 99999)}",
                error = null
            ))
        } else {
            Response.success(RegisterResponse(
                success = false,
                userId = null,
                error = "Проверьте введенные данные"
            ))
        }
    }
}