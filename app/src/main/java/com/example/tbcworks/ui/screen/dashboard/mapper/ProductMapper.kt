package com.example.tbcworks.ui.screen.dashboard.mapper

import com.example.tbcworks.domain.model.Product
import com.example.tbcworks.ui.screen.dashboard.model.ProductModel

fun Product.toPresentation(): ProductModel {
    return ProductModel(
        id = this.id,
        title = this.title,
        price = this.price,
        image = this.image,
        category = this.category
    )
}
