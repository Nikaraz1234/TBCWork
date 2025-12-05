package com.example.tbcworks.presentation.screens.user_list

data class UserModel(
    val id: Int,
    val fullName: String,
    val email: String,
    val activationStatus: Int,
    val lastActiveDescription: String,
    val lastActiveEpoch: Long,
    val profileImageUrl: String?,
    val cachedImagePath: String?
)