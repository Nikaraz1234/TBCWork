package com.example.tbcworks.data.auth

import kotlinx.serialization.Serializable
@Serializable
data class LoginResponse(
    val token: String?
)
@Serializable
data class RegisterResponse(
    val id: Int,
    val token: String
)
