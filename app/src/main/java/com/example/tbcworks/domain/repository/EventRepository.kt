package com.example.tbcworks.domain.repository

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.event.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
     fun getEvent(eventId: String) : Flow<Resource<Event>>
     fun getEvents() : Flow<Resource<List<Event>>>
}