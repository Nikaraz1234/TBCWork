package com.example.tbcworks.data.dto.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponseDto(
    val categories: List<CategoryDto>
)