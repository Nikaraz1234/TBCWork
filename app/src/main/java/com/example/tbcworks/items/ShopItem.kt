package com.example.tbcworks.items

import com.example.tbcworks.enums.CategoryType

data class ShopItem(
    val id: Int,
    val image: Int,
    val title: String,
    val price: Int,
    val categoryType: List<CategoryType> = listOf(CategoryType.ALL)
)
