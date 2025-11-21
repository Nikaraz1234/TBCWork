package com.example.tbcworks.presentation.screens.messenger

sealed class MessengerSideEffect {
    data class ShowError(val message: String) : MessengerSideEffect()
}