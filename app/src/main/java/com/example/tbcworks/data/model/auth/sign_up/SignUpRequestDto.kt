package com.example.tbcworks.data.model.auth

data class SignUpRequestDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val department: String,
    val password: String
)