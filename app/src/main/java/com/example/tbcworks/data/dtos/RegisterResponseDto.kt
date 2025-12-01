package com.example.tbcworks.data.auth.dtos

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDto(
    val id: Int,
    val token: String
)
