package com.example.tbcworks.presentation.screen.home

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.tbcworks.presentation.common.BaseViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.usecase.GetEventsUseCase
import com.example.tbcworks.presentation.mapper.toPresentation
import com.example.tbcworks.presentation.screen.home.model.CategoryModel
import com.example.tbcworks.presentation.screen.home.model.QaItem
import com.example.tbcworks.presentation.screen.home.model.Section
import java.time.format.DateTimeFormatter

@HiltViewModel
class EventHubViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
) : BaseViewModel<
        EventHubContract.State,
        EventHubContract.SideEffect,
        EventHubContract.Event
        >(
    initialState = EventHubContract.State()
) {

    fun onEvent(intent: EventHubContract.Event) {
        when (intent) {
            EventHubContract.Event.LoadData -> loadData()

            is EventHubContract.Event.CategoryClicked -> {
                sendSideEffect(
                    EventHubContract.SideEffect.NavigateToCategory(intent.category)
                )
            }

            is EventHubContract.Event.EventClicked -> {
                sendSideEffect(
                    EventHubContract.SideEffect.NavigateToEvent(intent.eventId)
                )
            }
        }
    }
    init {
        loadData()
    }
    val staticFaqs = listOf(
        QaItem(
            question = "How do I register for an event?",
            answer = "You can register by clicking the 'Register' button on the event details page."
        ),
        QaItem(
            question = "Can I cancel my registration?",
            answer = "Yes, you can cancel your registration up to 24 hours before the event."
        ),
        QaItem(
            question = "Are events free to attend?",
            answer = "Most events are free, but some premium workshops may have a fee."
        )
    )

    val staticCategories = listOf(
        CategoryModel(category = "Technology", eventCount = 12),
        CategoryModel(category = "Business", eventCount = 8),
        CategoryModel(category = "Health & Wellness", eventCount = 5),
        CategoryModel(category = "Education", eventCount = 7),
        CategoryModel(category = "Arts & Culture", eventCount = 3),
        CategoryModel(category = "Sports", eventCount = 4)
    )

    private fun loadData() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            getEventsUseCase().collect { result ->
                when(result) {
                    is Resource.Loading -> {
                        setState { copy(isLoading = true) }
                        Log.d("EventHubVM", "Loading events...")
                    }
                    is Resource.Success -> {
                        Log.d("EventHubVM", "Events loaded: ${result.data.size}")
                        val events = result.data.map { it.toPresentation() }
                        setState { copy(
                            isLoading = false,
                            upcomingEvents = events,
                            trendingEvents = events.take(5),
                            categories = staticCategories,
                            faqs = staticFaqs
                        )}
                    }
                    is Resource.Error -> {
                        Log.e("EventHubVM", "Error loading events: ${result.message}")
                        setState { copy(isLoading = false) }
                    }
                }
            }
        }
    }


}
