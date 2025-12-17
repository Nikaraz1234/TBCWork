package com.example.tbcworks.presentation.screens.profile

sealed class ProfileEvent {
    object LoadProfileData : ProfileEvent()
    object Logout : ProfileEvent()
    data class DeleteAccount(val current: String) : ProfileEvent()
    data class ChangePassword(val current: String, val newPassword: String) : ProfileEvent()
}