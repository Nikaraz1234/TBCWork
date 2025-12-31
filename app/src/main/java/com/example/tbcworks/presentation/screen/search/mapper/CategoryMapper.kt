package com.example.tbcworks.presentation.screen.search.mapper

import com.example.tbcworks.domain.model.Category
import com.example.tbcworks.presentation.screen.search.model.CategoryModel

fun List<Category>.toPresentation(
    depth: Int = 0,
    parentId: String? = null
): List<CategoryModel> {
    return flatMap { category ->
        val current = CategoryModel(
            id = category.id,
            title = category.name,
            depth = depth,
            parentId = parentId
        )

        val children =
            if (depth < 4)
                category.children.toPresentation(depth + 1, category.id)
            else emptyList()

        listOf(current) + children
    }
}

