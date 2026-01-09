package com.example.challenge.presentation.screen.connection.connection

import  com.example.challenge.presentation.screen.connection.String

data class Connection(
    val avatar: String,
    val email: String,
    val id: Int,
    val fullName: String,
    val isSelected: Boolean = false
)