package com.example.tbcworks.domain.splash

sealed class SplashSideEffect {
    data class ToHome(val email: String) : SplashSideEffect()
    data object ToLogin : SplashSideEffect()
}