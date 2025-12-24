package com.example.tbcworks.data.repository


import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.extension.asResource
import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.data.service.EventService
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.event.Event
import com.example.tbcworks.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepositoryImpl @Inject constructor(
    private val service: EventService,
    private val handleResponse: HandleResponse
) : EventRepository {

    override fun getEvent(eventId: String): Flow<Resource<Event>> = flow {
        emit(Resource.Loading)
        emitAll(
            handleResponse.safeApiCall { service.getEventById(eventId) }
                .map { it.asResource { dto -> dto.toDomain() } }
        )
    }

    override fun getEvents(): Flow<Resource<List<Event>>> = flow {
        emit(Resource.Loading)
        emitAll(
            handleResponse.safeApiCall { service.getEvents() }
                .map { it.asResource { list -> list.map { dto -> dto.toDomain() } } }
        )
    }
}