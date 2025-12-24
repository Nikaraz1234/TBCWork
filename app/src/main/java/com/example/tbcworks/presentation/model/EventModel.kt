package com.example.tbcworks.presentation.screen.model

import com.example.tbcworks.domain.model.event.AgendaItem
import com.example.tbcworks.domain.model.event.Capacity
import com.example.tbcworks.domain.model.event.Event
import com.example.tbcworks.domain.model.event.EventDate
import com.example.tbcworks.domain.model.event.Location
import com.example.tbcworks.domain.model.event.Organizer
import com.example.tbcworks.domain.model.event.Speaker

data class EventModel(
    val id: String,
    val title: String,
    val organizer: Organizer,
    val category: String,
    val description: String,
    val agenda: List<AgendaItem>,
    val imgUrl: String,
    val userStatus: Event.UserStatus,
    val registrationStatus: Event.RegistrationStatus,
    val speakers: List<Speaker>,
    val date: EventDate,
    val location: Location,
    val capacity: Capacity
)