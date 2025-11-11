package com.example.tbcworks.common.auth

data class LoginResponse(
    val token: String?
)
data class RegisterResponse(
    val user: User,
    val token: String
)
data class User(
    val id: Int,
    val email: String
)
