package com.example.tbcworks.presentation.screens.home

sealed class HomeSideEffect {
    data class ShowSnackBar(val message: String) : HomeSideEffect()
}