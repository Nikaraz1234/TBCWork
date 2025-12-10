package com.example.tbcworks.presentation.screens.home

sealed class HomeSideEffect {
    data class ShowMessage(val message: String) : HomeSideEffect()
}