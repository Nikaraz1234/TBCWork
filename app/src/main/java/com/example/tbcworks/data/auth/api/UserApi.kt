package com.example.tbcworks.data.auth.api

import com.example.tbcworks.data.auth.dtos.UserResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("users")
    suspend fun getUsers(@Query("page") page: Int = 1): UserResponseDto
}