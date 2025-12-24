package com.example.tbcworks.data.service

import com.example.tbcworks.data.model.event.EventResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface EventService {
    @GET("b3ef07dc-5cae-474d-9d3e-17e6947a31ea")
    suspend fun getEvents(): List<EventResponseDto>

    @GET("events/{id}")
    suspend fun getEventById(
        @Path("id") eventId: String
    ): EventResponseDto
}