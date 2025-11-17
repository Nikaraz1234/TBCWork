package com.example.tbcworks.data.auth.api

import com.example.tbcworks.data.auth.dtos.RegisterRequestDto
import com.example.tbcworks.data.auth.dtos.RegisterResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {
    @POST("register")
    suspend fun register(@Body request: RegisterRequestDto): Response<RegisterResponseDto>
}