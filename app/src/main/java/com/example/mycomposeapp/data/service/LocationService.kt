package com.example.mycomposeapp.data.service

import com.example.mycomposeapp.data.dto.LocationDto
import retrofit2.http.GET

interface LocationService {
    @GET("cards")
    suspend fun getLocations(): List<LocationDto>
}