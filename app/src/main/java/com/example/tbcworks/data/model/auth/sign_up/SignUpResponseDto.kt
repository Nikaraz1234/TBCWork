package com.example.tbcworks.data.model.auth.sign_up

data class SignUpResponseDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val department: String
)