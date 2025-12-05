package com.example.tbcworks.presentation.screens.user_list


data class UserListState(
    val users: List<UserModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)