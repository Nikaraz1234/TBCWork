package com.example.tbcworks.data.remote.model.login

import kotlinx.serialization.Serializable


@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String
)