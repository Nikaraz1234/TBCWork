package com.example.tbcworks.data.service

import com.example.tbcworks.data.model.StoryDto
import retrofit2.http.GET

interface StoryService {
    @GET("0f76d541-3832-4a3c-927a-0593e060d6da")
    suspend fun getStories() : List<StoryDto>
}