package com.example.challenge.presentation.screen.splash

sealed class SplashEvent {
    object NavigateToLogin : SplashEvent()
    object NavigateToConnections: SplashEvent()
}