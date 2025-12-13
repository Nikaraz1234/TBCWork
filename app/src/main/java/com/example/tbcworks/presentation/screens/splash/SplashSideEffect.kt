package com.example.tbcworks.presentation.screens.splash

sealed interface SplashSideEffect {
    data object ToHome : SplashSideEffect
    data object ToLogin : SplashSideEffect
}