package com.example.tbcworks.presentation.screens.home

sealed class HomeSideEffect {
    object NavigateToLogin : HomeSideEffect()
    object NavigateToDashboard : HomeSideEffect()
}