package com.example.tbcworks.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class GetRegisterResponse(
    val id: Int,
    val token: String
)
