package com.example.tbcworks.presentation.screens.profile

sealed class ProfileEvent {
    object LoadProfileData : ProfileEvent()
    object Logout : ProfileEvent()
    object DeleteAccount : ProfileEvent()
    data class ChangePassword(val current: String, val newPassword: String) : ProfileEvent()
}