package com.example.tbcworks.data.service

import com.example.tbcworks.data.model.LocationResponseDto
import retrofit2.http.GET

interface LocationService {
    @GET("d7c6d734-6080-4045-a196-7da16339b6d7")
    suspend fun getLocations(): List<LocationResponseDto>
}