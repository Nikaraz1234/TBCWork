package com.example.tbcworks.data.service

import com.example.tbcworks.data.model.PostDto
import retrofit2.http.GET

interface PostService {
    @GET("1e3f40b1-19a5-4986-ad60-fdc80c27234b")
    suspend fun getPosts() : List<PostDto>
}