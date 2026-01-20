package com.example.tbcworks.ui.screen.login

import com.example.tbcworks.domain.usecase.login.LoginUseCase
import com.example.tbcworks.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<
        LoginContract.State,
        LoginContract.SideEffect,
        LoginContract.Event
        >(
    initialState = LoginContract.State()
) {

    fun onEvent(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.EmailChanged -> {
                setState { copy(email = event.value, error = null) }
            }

            is LoginContract.Event.PasswordChanged -> {
                setState { copy(password = event.value, error = null) }
            }

            LoginContract.Event.LoginClicked -> {
                login()
            }
        }
    }

    private fun login() {
        val state = uiState.value

        handleResponse(
            apiCall = { loginUseCase(state.email, state.password) },
            onLoading = {
                setState { copy(isLoading = true, error = null) }
            },
            onSuccess = {
                setState { copy(isLoading = false) }
                sendSideEffect(LoginContract.SideEffect.NavigateToWelcome)
            },
            onError = { message ->
                setState { copy(isLoading = false, error = message) }
                sendSideEffect(LoginContract.SideEffect.ShowError(message))
            }
        )
    }
}