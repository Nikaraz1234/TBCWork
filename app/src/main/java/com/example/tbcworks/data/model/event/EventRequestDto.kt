package com.example.tbcworks.data.model

data class EventRequestDto(
    val title: String,
    val organizer: String,
    val category: String,
    val description: String,
    val agenda: List<AgendaItemDto>,
    val imgUrl: String,
    val speakers: List<SpeakerDto>,
    val date: DateDto,
    val location: LocationDto,
    val capacity: CapacityDto,
    val notifications: NotificationsDto
)