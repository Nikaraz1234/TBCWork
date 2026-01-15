package com.example.tbcworks.ui.screen.register_first

import com.example.tbcworks.ui.common.BaseViewModel


class RegisterFirstViewModel :
    BaseViewModel<RegisterFirstContract.State,
            RegisterFirstContract.SideEffect,
            RegisterFirstContract.Event>(
        initialState = RegisterFirstContract.State()
    ) {

    fun onEvent(intent: RegisterFirstContract.Event) {
        when (intent) {
            is RegisterFirstContract.Event.EnterEmail -> {
                setState { copy(email = intent.email) }
            }
            is RegisterFirstContract.Event.EnterPassword -> {
                setState { copy(password = intent.password) }
            }
            is RegisterFirstContract.Event.NextClicked -> {
                if (uiState.value.email.isBlank() || uiState.value.password.isBlank()) {
                    sendSideEffect(RegisterFirstContract.SideEffect.ShowError(FILL_FIELDS))
                } else {
                    sendSideEffect(RegisterFirstContract.SideEffect.NavigateToRegisterSecond)
                }
            }
        }
    }

    companion object{
        const val FILL_FIELDS = "Please fill all fields"
    }

}