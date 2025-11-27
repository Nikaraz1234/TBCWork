package com.example.tbcworks.presentation.screens.home

sealed interface HomeSideEffect {
    object NavigateToLogin : HomeSideEffect
    object NavigateToDashboard : HomeSideEffect
    object NavigateToUserInfo : HomeSideEffect
}