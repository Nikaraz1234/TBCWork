package com.example.tbcworks.presentation.screens.register

sealed class RegisterEvent {
    data class OnRegister(val email: String,val password: String,val repeatPassword: String) : RegisterEvent()
}