package com.example.tbcworks.data.mapper

import com.example.tbcworks.data.dto.product.ProductDto
import com.example.tbcworks.domain.model.Product

fun ProductDto.toDomain(): Product {
    return Product(
        id = this.id,
        title = this.title,
        price = this.price,
        image = this.image,
        category = this.category
    )
}
