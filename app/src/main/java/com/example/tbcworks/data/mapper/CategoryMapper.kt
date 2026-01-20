package com.example.tbcworks.data.mapper

import com.example.tbcworks.data.dto.category.CategoryDto
import com.example.tbcworks.domain.model.Category

fun CategoryDto.toDomain() : Category {
    return Category(
        id = this.id,
        category = this.category
    )
}