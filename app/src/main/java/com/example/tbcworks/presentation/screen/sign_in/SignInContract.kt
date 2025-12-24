package com.example.tbcworks.presentation.screen.sign_in

object SignInContract {

    data class State(
        val email: String = "",
        val password: String = "",
        val rememberMe: Boolean = false,

        val emailError: String? = null,
        val passwordError: String? = null,

        val isLoading: Boolean = false
    )

    sealed interface Event {
        data class SetEmail(val value: String) : Event
        data class SetPassword(val value: String) : Event
        data class RememberMeChanged(val value: Boolean) : Event
        data object SignInClicked : Event
        data object SignUpClicked : Event
    }

    sealed interface SideEffect {
        data object NavigateToHome : SideEffect
        data object NavigateToForgotPassword : SideEffect
        data object NavigateToSignUp : SideEffect
        data class ShowError(val message: String) : SideEffect
    }
}
