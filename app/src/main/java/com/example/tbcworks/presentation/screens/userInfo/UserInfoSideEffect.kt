package com.example.tbcworks.presentation.screens.userInfo

sealed interface UserInfoSideEffect {
    data class ShowUser(val firstName: String, val lastName: String, val email: String): UserInfoSideEffect
}