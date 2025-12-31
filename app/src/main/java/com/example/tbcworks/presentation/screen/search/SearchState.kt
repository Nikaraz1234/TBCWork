package com.example.tbcworks.presentation.screen.search

import com.example.tbcworks.presentation.screen.search.model.CategoryModel

data class SearchState(
    val query: String = "",
    val isLoading: Boolean = false,
    val allCategories: List<CategoryModel> = emptyList(),
    val visibleCategories: List<CategoryModel> = emptyList(),
    val currentDepth: Int = 0
)
