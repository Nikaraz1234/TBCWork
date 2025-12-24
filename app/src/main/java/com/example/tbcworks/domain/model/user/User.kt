package com.example.tbcworks.domain.model.user


data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val department: String
)