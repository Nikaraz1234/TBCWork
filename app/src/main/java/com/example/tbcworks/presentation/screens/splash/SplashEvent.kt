package com.example.tbcworks.domain.splash

sealed class SplashEvent {
    object StartSplash : SplashEvent()
    object StopSplash : SplashEvent()
}