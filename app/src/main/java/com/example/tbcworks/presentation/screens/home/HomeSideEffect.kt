package com.example.tbcworks.presentation.screens.home

sealed class HomeSideEffect {
    object NavigateToLogin : HomeSideEffect()
    data class ShowSnackBar(val message: String) : HomeSideEffect()
}