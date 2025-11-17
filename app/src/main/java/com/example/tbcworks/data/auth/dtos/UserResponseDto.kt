package com.example.tbcworks.data.auth.dtos

import com.example.tbcworks.data.auth.models.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDto(
    val data: List<User>
)