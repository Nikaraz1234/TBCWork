package com.example.tbcworks.ui.screen.register_first

import com.example.tbcworks.domain.usecase.register.RegisterUseCase
import com.example.tbcworks.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterFirstViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<
        RegisterFirstContract.State,
        RegisterFirstContract.SideEffect,
        RegisterFirstContract.Event
        >(initialState = RegisterFirstContract.State()) {

    fun onEvent(event: RegisterFirstContract.Event) {
        when (event) {
            is RegisterFirstContract.Event.EnterEmail -> {
                setState { copy(email = event.email, error = null) }
            }

            is RegisterFirstContract.Event.EnterPassword -> {
                setState { copy(password = event.password, error = null) }
            }

            RegisterFirstContract.Event.NextClicked -> {
                if (uiState.value.email.isBlank() || uiState.value.password.isBlank()) {
                    sendSideEffect(RegisterFirstContract.SideEffect.ShowError(FILL_FIELDS))
                } else {
                    register()
                }
            }
        }
    }

    private fun register() {
        val state = uiState.value

        handleResponse(
            apiCall = { registerUseCase(state.email, state.password) },
            onLoading = { _ ->
                setState { copy(isLoading = true, error = null) }
            },
            onSuccess = { user ->
                setState { copy(isLoading = false) }
                sendSideEffect(RegisterFirstContract.SideEffect.NavigateToLogin)
            },
            onError = { message ->
                setState { copy(isLoading = false, error = message) }
                sendSideEffect(RegisterFirstContract.SideEffect.ShowError(message))
            }
        )
    }

    companion object {
        const val FILL_FIELDS = "Please fill all fields"
    }
}
