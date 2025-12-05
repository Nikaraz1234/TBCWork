package com.example.tbcworks.data.service

import com.example.tbcworks.data.model.LocationDto
import retrofit2.http.GET

interface LocationService {
    @GET("v1/e3215354-6784-4bae-9bb9-25b39360971b")
    suspend fun getLocations(): List<LocationDto>
}