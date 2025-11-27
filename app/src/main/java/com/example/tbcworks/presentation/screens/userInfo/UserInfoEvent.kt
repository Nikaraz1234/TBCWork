package com.example.tbcworks.presentation.screens.userInfo

sealed class UserInfoEvent {
    data class SaveUser(val firstName: String, val lastName: String, val email: String) : UserInfoEvent()
    object ReadUser : UserInfoEvent()
}