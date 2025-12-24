package com.example.tbcworks.presentation.screen.register.model

data class SignUpModel(
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