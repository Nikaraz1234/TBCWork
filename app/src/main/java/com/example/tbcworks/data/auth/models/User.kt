package com.example.tbcworks.data.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val email: String
)
