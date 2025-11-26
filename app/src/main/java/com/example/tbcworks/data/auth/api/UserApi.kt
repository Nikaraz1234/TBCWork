package com.example.tbcworks.data.auth.api

import com.example.tbcworks.data.auth.dtos.UserResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") limit: Int = 6
    ): Response<UserResponseDto>
}