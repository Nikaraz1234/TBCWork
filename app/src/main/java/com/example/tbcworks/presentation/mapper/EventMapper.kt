package com.example.tbcworks.presentation.mapper

import com.example.tbcworks.domain.model.event.Address
import com.example.tbcworks.domain.model.event.AgendaItem
import com.example.tbcworks.domain.model.event.Capacity
import com.example.tbcworks.domain.model.event.Event
import com.example.tbcworks.domain.model.event.EventDate
import com.example.tbcworks.domain.model.event.Location
import com.example.tbcworks.domain.model.event.Organizer
import com.example.tbcworks.domain.model.event.Speaker
import com.example.tbcworks.domain.model.event.SpeakerInfo
import com.example.tbcworks.presentation.model.AddressModel
import com.example.tbcworks.presentation.model.AgendaModel
import com.example.tbcworks.presentation.model.CapacityModel
import com.example.tbcworks.presentation.model.EventDateModel
import com.example.tbcworks.presentation.model.EventModel
import com.example.tbcworks.presentation.model.LocationModel
import com.example.tbcworks.presentation.model.OrganizerModel
import com.example.tbcworks.presentation.model.SpeakerInfoModel
import com.example.tbcworks.presentation.model.SpeakerModel

fun Event.toPresentation(): EventModel {
    return EventModel(
        id = this.id,
        title = this.title,
        organizer = this.organizer.toPresentation(),
        category = this.category,
        description = this.description,
        agenda = this.agenda.map { it.toPresentation() },
        imgUrl = this.imgUrl,
        userStatus = this.userStatus,
        registrationStatus = this.registrationStatus,
        speakers = this.speakers.map { it.toPresentation() },
        date = this.date.toPresentation(),
        location = this.location.toPresentation(),
        capacity = this.capacity.toPresentation()
    )
}

fun EventModel.toDomain(): Event {
    return Event(
        id = this.id,
        title = this.title,
        organizer = this.organizer.toDomain(),
        category = this.category,
        description = this.description,
        agenda = this.agenda.map { it.toDomain() },
        imgUrl = this.imgUrl,
        userStatus = this.userStatus,
        registrationStatus = this.registrationStatus,
        speakers = this.speakers.map { it.toDomain() },
        date = this.date.toDomain(),
        location = this.location.toDomain(),
        capacity = this.capacity.toDomain()
    )
}

// Address
fun Address.toPresentation(): AddressModel = AddressModel(
    street = street,
    city = city
)

// AgendaItem
fun AgendaItem.toPresentation(): AgendaModel = AgendaModel(
    startTime = startTime,
    duration = duration,
    title = title,
    description = description,
    activityType = activityType,
    activityLocation = activityLocation
)

// Capacity
fun Capacity.toPresentation(): CapacityModel = CapacityModel(
    maxCapacity = maxCapacity,
    currentlyRegistered = currentlyRegistered,
    enableWaitlist = enableWaitlist,
    waitlistCapacity = waitlistCapacity,
    autoApprove = autoApprove
)

// EventDate
fun EventDate.toPresentation(): EventDateModel = EventDateModel(
    eventType = eventType,
    startDate = startDate,
    endDate = endDate,
    registerDeadline = registerDeadline
)

// Location
fun Location.toPresentation(): LocationModel = LocationModel(
    type = type,
    venueName = venueName,
    address = address.toPresentation(),
    roomNumber = roomNumber,
    floor = floor,
    additionalNotes = additionalNotes
)

// Organizer
fun Organizer.toPresentation(): OrganizerModel = OrganizerModel(
    fullName = fullName,
    jobTitle = jobTitle,
    department = department,
    profileImgUrl = profileImgUrl,
    email = email
)

// Speaker
fun Speaker.toPresentation(): SpeakerModel = SpeakerModel(
    fullName = fullName,
    role = role,
    linkedinUrl = linkedinUrl,
    websiteUrl = websiteUrl,
    description = description,
    imgUrl = imgUrl
)

// SpeakerInfo
fun SpeakerInfo.toPresentation(): SpeakerInfoModel = SpeakerInfoModel(
    name = name,
    jobTitle = jobTitle
)

// Address
fun AddressModel.toDomain(): Address = Address(
    street = street,
    city = city
)

// AgendaItem
fun AgendaModel.toDomain(): AgendaItem = AgendaItem(
    startTime = startTime,
    duration = duration,
    title = title,
    description = description,
    activityType = activityType,
    activityLocation = activityLocation
)

// Capacity
fun CapacityModel.toDomain(): Capacity = Capacity(
    maxCapacity = maxCapacity,
    currentlyRegistered = currentlyRegistered,
    enableWaitlist = enableWaitlist,
    waitlistCapacity = waitlistCapacity,
    autoApprove = autoApprove
)

// EventDate
fun EventDateModel.toDomain(): EventDate = EventDate(
    eventType = eventType,
    startDate = startDate,
    endDate = endDate,
    registerDeadline = registerDeadline
)

// Location
fun LocationModel.toDomain(): Location = Location(
    type = type,
    venueName = venueName,
    address = address.toDomain(),
    roomNumber = roomNumber,
    floor = floor,
    additionalNotes = additionalNotes
)

// Organizer
fun OrganizerModel.toDomain(): Organizer = Organizer(
    fullName = fullName,
    jobTitle = jobTitle,
    department = department,
    profileImgUrl = profileImgUrl,
    email = email
)

// Speaker
fun SpeakerModel.toDomain(): Speaker = Speaker(
    fullName = fullName,
    role = role,
    linkedinUrl = linkedinUrl,
    websiteUrl = websiteUrl,
    description = description,
    imgUrl = imgUrl
)

// SpeakerInfo
fun SpeakerInfoModel.toDomain(): SpeakerInfo = SpeakerInfo(
    name = name,
    jobTitle = jobTitle
)
