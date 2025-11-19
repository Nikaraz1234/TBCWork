package com.example.tbcworks.presentation.screens.login

sealed class LoginEvent {
    data class OnLogin(val email:String, val password:String, val rememberMe: Boolean) : LoginEvent()
    data object  OnRegister : LoginEvent()
}