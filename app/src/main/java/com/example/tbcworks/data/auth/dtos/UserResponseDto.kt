package com.example.tbcworks.data.auth.dtos

import com.example.tbcworks.data.auth.models.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDto(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<User>
)