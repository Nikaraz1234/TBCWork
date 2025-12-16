package com.example.tbcworks.presentation.screens.profile

data class ProfileState(
    val email: String = "",
    val balance: Double = 0.0,
    val transactionsCount: Int = 0,
    val potsCount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)