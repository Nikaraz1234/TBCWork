package com.example.tbcworks.domain.service

import com.example.tbcworks.domain.model.Category
import javax.inject.Inject

class CategoryFilter @Inject constructor() {

     fun filter(
        categories: List<Category>,
        query: String
    ): List<Category> {
        if (query.isBlank()) return categories

        return categories.mapNotNull { filterCategory(it, query) }
    }

    private fun filterCategory(
        category: Category,
        query: String
    ): Category? {
        val filteredChildren = category.children
            .mapNotNull { filterCategory(it, query) }

        return if (
            category.name.contains(query, ignoreCase = true)
            || filteredChildren.isNotEmpty()
        ) {
            category.copy(children = filteredChildren)
        } else {
            null
        }
    }
}