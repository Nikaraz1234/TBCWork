package com.example.tbcworks.presentation.screen.search

import com.example.tbcworks.presentation.screen.search.model.CategoryModel

sealed class SearchEvent {
    data class OnQueryChange(val query: String) : SearchEvent()
    data class OnCategoryClick(val category: CategoryModel) : SearchEvent()
}