package com.example.tbcworks.presentation.screen.browse_event

import com.example.tbcworks.domain.usecase.GetEventsUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import com.example.tbcworks.presentation.mapper.toPresentation
import com.example.tbcworks.presentation.model.EventModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BrowseEventViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
) : BaseViewModel<BrowseEventContract.State, BrowseEventContract.SideEffect, BrowseEventContract.Event>(
    BrowseEventContract.State()
) {

    private var allEvents: List<EventModel> = emptyList()
    private var selectedCategory: String? = null

    init {
        loadCategories()
        loadEvents()
    }

    private fun loadCategories() {
        val categories = listOf(
            "TeamBuilding",
            "Sports",
            "Workshops",
            "HappyFridays",
            "Cultural",
            "Wellness"
        )
        setState { copy(categories = categories) }
    }

    private fun loadEvents() {
        handleResponse(
            apiCall = { getEventsUseCase() },
            onSuccess = { events ->
                allEvents = events.map { it.toPresentation() }
                setState {
                    copy(
                        events = allEvents,
                        filteredEvents = allEvents,
                        isLoading = false
                    )
                }
            },
            onError = { message ->
                sendSideEffect(BrowseEventContract.SideEffect.ShowError(message))
            },
            onLoading = { setState { copy(isLoading = true) } }
        )
    }

    fun onEvent(event: BrowseEventContract.Event) {
        when (event) {
            is BrowseEventContract.Event.SearchQueryChanged -> {
                setState { copy(searchQuery = event.query) }
                applyFilters()
            }
            is BrowseEventContract.Event.CategorySelected -> {
                selectedCategory = event.category
                setState { copy(selectedCategory = event.category) }
                applyFilters()
            }
        }
    }

    private fun applyFilters() {
        val query = uiState.value.searchQuery.lowercase()
        val filtered = allEvents.filter { event ->
            val matchesCategory = selectedCategory?.let { it == event.category } ?: true
            val matchesQuery = event.title.lowercase().contains(query)
            matchesCategory && matchesQuery
        }
        setState { copy(filteredEvents = filtered) }
    }
}
