package com.example.tbcworks.data.auth.api

import com.example.tbcworks.data.auth.dtos.LoginRequestDto
import com.example.tbcworks.data.auth.dtos.LoginResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("login")
    suspend fun login(@Body request: LoginRequestDto): Response<LoginResponseDto>
}