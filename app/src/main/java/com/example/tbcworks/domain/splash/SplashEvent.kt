package com.example.tbcworks.domain.splash

sealed class SplashEvent {
    object CheckToken : SplashEvent()
}