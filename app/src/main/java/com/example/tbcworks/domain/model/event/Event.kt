package com.example.tbcworks.domain.model

data class Event(
    val id: String,
    val title: String,
    val organizer: Organizer,
    val category: String,
    val description: String,
    val agenda: List<AgendaItem>,
    val imgUrl: String,
    val userStatus: UserStatus,
    val registrationStatus: RegistrationStatus,
    val speakers: List<Speaker>,
    val date: EventDate,
    val location: Location,
    val capacity: Capacity
){
    enum class UserStatus { NOT_REGISTERED, WAITLIST, APPROVED }
    enum class RegistrationStatus { OPEN, CLOSED }
}

