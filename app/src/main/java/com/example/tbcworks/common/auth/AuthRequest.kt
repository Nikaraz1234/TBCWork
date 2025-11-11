package com.example.tbcworks.common.auth

data class LoginRequest(
    val email: String,
    val password: String
)
data class RegisterRequest(
    val email: String,
    val password: String
)
