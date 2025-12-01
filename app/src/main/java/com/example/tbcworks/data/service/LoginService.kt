package com.example.tbcworks.data.api

import com.example.tbcworks.data.dtos.LoginRequestDto
import com.example.tbcworks.data.dtos.LoginResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    suspend fun login(@Body request: LoginRequestDto): Response<LoginResponseDto>
}