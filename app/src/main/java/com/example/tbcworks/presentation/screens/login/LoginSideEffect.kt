package com.example.tbcworks.presentation.screens.login

sealed class LoginSideEffect {
    object ToHome : LoginSideEffect()
    object ToRegister : LoginSideEffect()
    data class ShowMessage(val message: String) : LoginSideEffect()
}