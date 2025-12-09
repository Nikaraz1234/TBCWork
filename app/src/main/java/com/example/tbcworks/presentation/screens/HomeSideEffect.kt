package com.example.tbcworks.presentation.screens

sealed class HomeSideEffect {
    data class ShowMessage(val message: String) : HomeSideEffect()
}