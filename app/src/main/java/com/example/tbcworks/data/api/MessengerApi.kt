package com.example.tbcworks.data.api

import com.example.tbcworks.data.models.User
import retrofit2.http.GET

interface MessengerApi {
    @GET("v1/d7d9436b-21c5-43f7-82f9-2334163351cf")
    suspend fun getUsers(): List<User>
}