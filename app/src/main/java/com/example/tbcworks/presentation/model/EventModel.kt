package com.example.tbcworks.presentation.model

import com.example.tbcworks.domain.model.event.Event

data class EventModel(
    val id: String,
    val title: String,
    val organizer: OrganizerModel,
    val category: String,
    val description: String,
    val agenda: List<AgendaModel>,
    val imgUrl: String,
    val userStatus: Event.UserStatus,
    val registrationStatus: Event.RegistrationStatus,
    val speakers: List<SpeakerModel>,
    val date: EventDateModel,
    val location: LocationModel,
    val capacity: CapacityModel
)