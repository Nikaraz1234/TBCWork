package com.example.tbcworks.presentation.screens.login

sealed interface LoginEvent {

    data class OnLoginClick(val email: String, val password: String) : LoginEvent

    object OnSignUpClick : LoginEvent
}