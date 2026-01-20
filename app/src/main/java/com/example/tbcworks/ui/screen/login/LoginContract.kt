package com.example.tbcworks.ui.screen.login

object LoginContract {

    data class State(
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed interface Event {
        data class EmailChanged(val value: String) : Event
        data class PasswordChanged(val value: String) : Event
        data object LoginClicked : Event
    }

    sealed interface SideEffect {
        data object NavigateToWelcome : SideEffect
        data class ShowError(val message: String) : SideEffect
    }
}
