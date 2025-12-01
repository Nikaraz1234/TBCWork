package com.example.tbcworks.data.api

import com.example.tbcworks.data.dtos.UserResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") limit: Int = 6
    ): Response<UserResponseDto>
}