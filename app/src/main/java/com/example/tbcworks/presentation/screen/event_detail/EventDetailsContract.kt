package com.example.tbcworks.presentation.screen.event_detail

import com.example.tbcworks.domain.model.event.AgendaItem
import com.example.tbcworks.domain.model.event.Speaker
import com.example.tbcworks.presentation.model.AgendaModel
import com.example.tbcworks.presentation.model.SpeakerModel

object EventDetailsContract {

    data class State(
        val isLoading: Boolean = false,

        val title: String = "",
        val bannerUrl: String = "",
        val eventDate: String = "",
        val eventTime: String = "",
        val location: String = "",
        val capacity: String = "",

        val isRegistrationOpen: Boolean = false,
        val registerCloseText: String = "",

        val aboutDescription: String = "",

        val agenda: List<AgendaModel> = emptyList(),
        val speakers: List<SpeakerModel> = emptyList(),

        val errorMessage: String? = null
    )

    sealed interface Event {
        data class Load(val eventId: String) : Event
        object BackClicked : Event
        object RegisterClicked : Event
    }

    sealed interface Effect {

        // Navigation
        object NavigateBack : Effect
        // Feedback
        data class ShowError(val message: String) : Effect
        data class ShowMessage(val message: String) : Effect
    }
}
