package com.example.tbcworks.presentation.screens.user_list

interface UserListSideEffect {
    data class ShowError(val message: String) : UserListSideEffect

}