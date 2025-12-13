package com.example.tbcworks.presentation.screens.signup

sealed interface SignUpEvent {
    data class OnSignUpClick(val email: String, val password: String) : SignUpEvent
    object OnLoginClick : SignUpEvent
}