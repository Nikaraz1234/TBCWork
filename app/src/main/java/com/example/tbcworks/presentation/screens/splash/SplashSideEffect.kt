package com.example.tbcworks.presentation.screens.splash

sealed interface SplashSideEffect {
    data class ToHome(val email: String) : SplashSideEffect
    data object ToLogin : SplashSideEffect
}