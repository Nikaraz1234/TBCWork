package com.example.tbcworks.data.auth.dtos

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String?
)