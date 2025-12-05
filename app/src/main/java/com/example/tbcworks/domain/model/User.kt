package com.example.tbcworks.domain.model

data class User(
    val id: Int,
    val fullName: String,
    val email: String,
    val activationStatus: Int,
    val lastActiveDescription: String,
    val lastActiveEpoch: Long,
    val profileImageUrl: String?,
    val cachedImagePath: String?
)

