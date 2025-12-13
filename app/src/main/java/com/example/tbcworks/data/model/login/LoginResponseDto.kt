package com.example.tbcworks.data.model.login

import com.example.tbcworks.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String,
    val user: User
)