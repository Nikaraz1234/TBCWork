package com.example.tbcworks.data.service

import com.example.tbcworks.data.dtos.RegisterRequestDto
import com.example.tbcworks.data.dtos.RegisterResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("register")
    suspend fun register(@Body request: RegisterRequestDto): Response<RegisterResponseDto>
}