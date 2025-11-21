package com.example.tbcworks.presentation.screens.messenger

import com.example.tbcworks.data.models.User

data class MessengerState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false
)
