package com.example.tbcworks.data.mapper

import com.example.tbcworks.data.model.event.AddressDto
import com.example.tbcworks.data.model.event.AgendaAdditionalInfoDto
import com.example.tbcworks.data.model.event.AgendaItemDto
import com.example.tbcworks.data.model.event.CapacityDto
import com.example.tbcworks.data.model.event.DateDto
import com.example.tbcworks.data.model.event.EventResponseDto
import com.example.tbcworks.data.model.event.LocationDto
import com.example.tbcworks.data.model.event.OrganizerDto
import com.example.tbcworks.data.model.event.SpeakerDto
import com.example.tbcworks.data.model.event.SpeakerInfoDto
import com.example.tbcworks.domain.model.event.Address
import com.example.tbcworks.domain.model.event.AgendaAdditionalInfo
import com.example.tbcworks.domain.model.event.AgendaItem
import com.example.tbcworks.domain.model.event.Capacity
import com.example.tbcworks.domain.model.event.Event
import com.example.tbcworks.domain.model.event.EventDate
import com.example.tbcworks.domain.model.event.Location
import com.example.tbcworks.domain.model.event.Organizer
import com.example.tbcworks.domain.model.event.Speaker
import com.example.tbcworks.domain.model.event.SpeakerInfo
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

private fun String.toDate(): LocalDateTime =
    Instant.parse(this).toLocalDateTime(TimeZone.currentSystemDefault())

fun EventResponseDto.toDomain(): Event = Event(
    id = id,
    title = title,
    organizer = organizer.toDomain(),
    category = category,
    description = description,
    agenda = agenda.map { it.toDomain() },
    imgUrl = imgUrl,
    userStatus = userStatus.toDomainUserStatus(),
    registrationStatus = registrationStatus.toDomainRegistrationStatus(),
    speakers = speakers.map { it.toDomain() },
    date = date.toDomain(),
    location = location.toDomain(),
    capacity = capacity.toDomain()
)

fun OrganizerDto.toDomain(): Organizer = Organizer(
    fullName = fullName,
    jobTitle = jobTitle,
    department = department,
    profileImgUrl = profileImgUrl,
    email = email
)

fun AgendaItemDto.toDomain(): AgendaItem = AgendaItem(
    startTime = startTime.toLocalDateTime(),
    duration = duration,
    title = title,
    description = description,
    activityType = activityType,
    activityLocation = activityLocation,
)

fun AgendaAdditionalInfoDto.toDomain(): AgendaAdditionalInfo = AgendaAdditionalInfo(
    title = title,
    roomNumber = roomNumber,
    speakerInfo = speakerInfo.toDomain()
)

fun SpeakerInfoDto.toDomain(): SpeakerInfo = SpeakerInfo(
    name = name,
    jobTitle = jobTitle
)

fun SpeakerDto.toDomain(): Speaker = Speaker(
    fullName = fullName,
    role = role,
    linkedinUrl = linkedinUrl,
    websiteUrl = websiteUrl,
    description = description,
    imgUrl = imgUrl
)

fun DateDto.toDomain(): EventDate = EventDate(
    eventType = eventType,
    startDate = startDate,
    endDate = endDate,
    registerDeadline = registerDeadline
)

fun LocationDto.toDomain(): Location = Location(
    type = type,
    venueName = venueName,
    address = address.toDomain(),
    roomNumber = roomNumber,
    floor = floor,
    additionalNotes = additionalNotes
)

fun AddressDto.toDomain(): Address = Address(
    street = street,
    city = city
)

fun CapacityDto.toDomain(): Capacity = Capacity(
    maxCapacity = maxCapacity,
    currentlyRegistered = currentlyRegistered,
    enableWaitlist = enableWaitlist,
    waitlistCapacity = waitlistCapacity,
    autoApprove = autoApprove,
)
fun String.toDomainUserStatus() = when (this.uppercase()) {
    "WAITLIST" -> Event.UserStatus.WAITLIST
    "APPROVED" -> Event.UserStatus.APPROVED
    else -> Event.UserStatus.NOT_REGISTERED
}

fun String.toDomainRegistrationStatus() = when (this.uppercase()) {
    "OPEN" -> Event.RegistrationStatus.OPEN
    "CLOSED" -> Event.RegistrationStatus.CLOSED
    else -> Event.RegistrationStatus.CLOSED
}