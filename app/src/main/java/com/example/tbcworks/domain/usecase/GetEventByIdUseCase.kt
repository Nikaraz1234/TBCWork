package com.example.tbcworks.domain.usecase

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.event.Event
import com.example.tbcworks.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventByIdUseCase @Inject constructor(
    private val repository: EventRepository
) {
    operator fun invoke(eventId: String): Flow<Resource<Event>> {
        return repository.getEvent(eventId)
    }
}