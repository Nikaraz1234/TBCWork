package com.example.tbcworks.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String?
)