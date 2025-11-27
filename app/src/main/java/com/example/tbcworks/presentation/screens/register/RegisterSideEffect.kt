package com.example.tbcworks.presentation.screens.register


sealed interface RegisterSideEffect {
    data class ToLogin(val username: String, val password: String) : RegisterSideEffect
    data class ShowMessage(val message: String) : RegisterSideEffect
}