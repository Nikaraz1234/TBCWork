package com.example.tbcworks.presentation.screens.user_list

sealed class UserListEvent {
    object LoadUsers : UserListEvent()
    object RefreshUsers : UserListEvent()

}