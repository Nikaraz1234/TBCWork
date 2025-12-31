package com.example.tbcworks.data.mapper

import com.example.tbcworks.data.dto.CategoryResponseDto
import com.example.tbcworks.domain.model.Category

fun CategoryResponseDto.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        nameDe = nameDe,
        createdAt = createdAt,
        orderId = orderId,
        children = children.map { it.toDomain() }
    )
}