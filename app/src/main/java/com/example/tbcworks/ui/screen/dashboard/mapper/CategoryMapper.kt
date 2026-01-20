package com.example.tbcworks.ui.screen.dashboard.mapper

import com.example.tbcworks.domain.model.Category
import com.example.tbcworks.ui.screen.dashboard.model.CategoryModel

fun Category.toPresentation() : CategoryModel {
    return CategoryModel(
        id = this.id,
        category = this.category
    )
}