package com.example.tbcworks.data.service

import com.example.tbcworks.data.model.UserDto
import retrofit2.http.GET

interface UserService {
    @GET("v1/3668d139-e182-4fe2-b909-6259524117cb")
    suspend fun getUsers(): List<UserDto>
}