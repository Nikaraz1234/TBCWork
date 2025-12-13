package com.example.tbcworks.data.model.login

import kotlinx.serialization.Serializable


@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String
)