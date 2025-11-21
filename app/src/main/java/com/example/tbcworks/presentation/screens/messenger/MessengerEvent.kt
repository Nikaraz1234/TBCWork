package com.example.tbcworks.presentation.screens.messenger

sealed class MessengerEvent {
    data class FilterUsers(val searchText: String) : MessengerEvent()
    object LoadUsers : MessengerEvent()
}