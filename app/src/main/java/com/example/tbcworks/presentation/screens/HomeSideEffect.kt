package com.example.tbcworks.presentation.screens

sealed interface HomeSideEffect {
    object ShowError : HomeSideEffect
}