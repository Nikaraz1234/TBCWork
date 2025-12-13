package com.example.tbcworks.presentation.screens.login
sealed interface LoginSideEffect {

    data class ShowError(val message: String) : LoginSideEffect

    object NavigateToHome : LoginSideEffect

    object NavigateToSignUp : LoginSideEffect

    object ShowLoading : LoginSideEffect

    object HideLoading : LoginSideEffect
}