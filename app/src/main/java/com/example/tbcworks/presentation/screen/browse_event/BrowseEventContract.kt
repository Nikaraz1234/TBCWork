package com.example.tbcworks.presentation.screen.browse_event

import com.example.tbcworks.presentation.model.EventModel
import com.example.tbcworks.presentation.screen.home.model.CategoryModel

object BrowseEventContract {

    data class State(
        val searchQuery: String = "",
        val categories: List<String> = emptyList(),
        val events: List<EventModel> = emptyList(),
        val filteredEvents: List<EventModel> = emptyList(),
        val selectedCategory: String? = null,
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    sealed interface SideEffect {
        data class ShowError(val message: String) : SideEffect
    }

    sealed interface Event {
        data class SearchQueryChanged(val query: String) : Event
        data class CategorySelected(val category: String) : Event
    }
}
