package com.example.tbcworks.data.model


data class EventResponseDto(
    val id: String,
    val title: String,
    val organizer: OrganizerDto,
    val category: String,
    val description: String,
    val agenda: List<AgendaItemDto>,
    val imgUrl: String,
    val userStatus: String, // not Register/Waitlist/Approved
    val registrationStatus: String,
    val speakers: List<SpeakerDto>,
    val date: DateDto,
    val location: LocationDto,
    val capacity: CapacityDto
)