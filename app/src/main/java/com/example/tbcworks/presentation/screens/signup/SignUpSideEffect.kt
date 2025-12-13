package com.example.tbcworks.presentation.screens.signup

sealed interface SignUpSideEffect {
    object NavigateToHome : SignUpSideEffect
    object NavigateToLogin : SignUpSideEffect
    data class ShowError(val message: String) : SignUpSideEffect
}