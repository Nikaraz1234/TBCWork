package com.example.tbcworks.presentation.screen.search.model

data class CategoryModel(
    val id: String,
    val title: String,
    val depth: Int,
    val parentId: String? = null,
    val isExpanded: Boolean = false
)