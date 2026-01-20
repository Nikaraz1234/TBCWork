package com.example.tbcworks.data.dto.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: Int,
    val category: String
)