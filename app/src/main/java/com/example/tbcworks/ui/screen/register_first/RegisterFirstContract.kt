package com.example.tbcworks.ui.screen.register_first

object RegisterFirstContract {

    data class State(
        val email: String = "",
        val password: String = "",
        val isLoading : Boolean = false,
        val error: String? = ""
    )

    sealed class Event {
        data class EnterEmail(val email: String) : Event()
        data class EnterPassword(val password: String) : Event()
        object NextClicked : Event()
    }

    sealed class SideEffect {
        object NavigateToLogin : SideEffect()
        data class ShowError(val message: String) : SideEffect()
    }
}
