package com.example.tbcworks.presentation.screens.profile

sealed class ProfileSideEffect {
    data class ShowSnackBar(val message: String) : ProfileSideEffect()
    object NavigateToLogin : ProfileSideEffect()
}