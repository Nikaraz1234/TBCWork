package com.example.tbcworks.domain.model.auth

data class SignUp(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val otpCode: String = "",
    val department: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPolicyAccepted: Boolean = false
)