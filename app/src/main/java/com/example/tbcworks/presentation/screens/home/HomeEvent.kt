package com.example.tbcworks.presentation.screens.home

sealed class HomeEvent {
    object LogoutClicked : HomeEvent()
    object UserListClicked : HomeEvent()
    object UserInfoClicked : HomeEvent()
}