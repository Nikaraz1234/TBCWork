package com.example.mycomposeapp.data.service

import com.example.mycomposeapp.data.dto.PostDto
import retrofit2.http.GET

interface PostService {
    @GET("post")
    suspend fun getPosts() : List<PostDto>
}