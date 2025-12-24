package com.example.tbcworks.presentation.screen.sign_in

import com.example.tbcworks.domain.usecase.auth.SignInUseCase
import com.example.tbcworks.domain.usecase.validation.ValidateEmailFormatUseCase
import com.example.tbcworks.domain.usecase.validation.ValidateNotEmptyUseCase
import com.example.tbcworks.domain.usecase.validation.ValidatePasswordLengthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.tbcworks.presentation.common.BaseViewModel
import com.example.tbcworks.domain.validation.*
import com.example.tbcworks.presentation.screen.sign_in.mapper.toDomain
import com.example.tbcworks.presentation.screen.sign_in.model.SignInModel

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val validateNotEmpty: ValidateNotEmptyUseCase,
    private val validateEmail: ValidateEmailFormatUseCase,
    private val validatePasswordLength: ValidatePasswordLengthUseCase,
    private val signInUseCase: SignInUseCase
) : BaseViewModel<
        SignInContract.State,
        SignInContract.SideEffect,
        SignInContract.Event
        >(
    initialState = SignInContract.State()
) {

    fun onEvent(intent: SignInContract.Event) {
        when (intent) {
            is SignInContract.Event.RememberMeChanged ->
                setState { copy(rememberMe = intent.value) }

            SignInContract.Event.SignInClicked ->
                submit()

            is SignInContract.Event.SetEmail ->
                setState { copy(email = intent.value, emailError = null) }

            is SignInContract.Event.SetPassword ->
                setState { copy(password = intent.value, passwordError = null) }

            SignInContract.Event.SignUpClicked -> sendSideEffect(SignInContract.SideEffect.NavigateToSignUp)
        }
    }

    private fun submit() {
        val state = uiState.value

        // 1️⃣ Validation
        val emailEmpty = validateNotEmpty.validate(state.email)
        val emailFormat = validateEmail.validate(state.email)
        val passwordValid = validatePasswordLength.validate(state.password)

        var hasError = false

        if (emailEmpty is ValidationResult.Error) {
            setState { copy(emailError = emailEmpty.message) }
            hasError = true
        } else if (emailFormat is ValidationResult.Error) {
            setState { copy(emailError = emailFormat.message) }
            hasError = true
        }

        if (passwordValid is ValidationResult.Error) {
            setState { copy(passwordError = passwordValid.message) }
            hasError = true
        }

        if (hasError) return

        handleResponse(
            apiCall = { signInUseCase(SignInModel(state.email, state.password).toDomain()) },
            onSuccess = { token ->
                setState { copy(isLoading = false) }
                sendSideEffect(SignInContract.SideEffect.NavigateToHome)
                // Optional: save token if needed
            },
            onError = { message ->
                setState { copy(isLoading = false) }
                sendSideEffect(SignInContract.SideEffect.ShowError(message))
            },
            onLoading = {
                setState { copy(isLoading = true) }
            }
        )
    }


}
