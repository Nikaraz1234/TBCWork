package com.example.tbcworks.presentation.screens.splash

sealed class SplashEvent {
    object StartSplash : SplashEvent()
    object StopSplash : SplashEvent()
}