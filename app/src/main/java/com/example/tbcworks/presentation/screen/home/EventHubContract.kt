package com.example.tbcworks.presentation.screen.home

import com.example.tbcworks.presentation.model.EventModel
import com.example.tbcworks.presentation.screen.home.model.CategoryModel
import com.example.tbcworks.presentation.screen.home.model.QaItem
import com.example.tbcworks.presentation.screen.home.model.Section

object EventHubContract {

    data class State(
        val upcomingEvents: List<EventModel> = emptyList(),
        val categories: List<CategoryModel> = emptyList(),
        val trendingEvents: List<EventModel> = emptyList(),
        val faqs: List<QaItem> = emptyList(),
        val isLoading: Boolean = false
    )

    sealed interface Event {
        data object LoadData : Event
        data class CategoryClicked(val category: String) : Event
        data class EventClicked(val eventId: String) : Event
    }

    sealed interface SideEffect {
        data class NavigateToEvent(val eventId: String) : SideEffect
        data class NavigateToCategory(val category: String) : SideEffect
    }
}

