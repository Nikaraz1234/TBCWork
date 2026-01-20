package com.example.tbcworks.data.service

import com.example.tbcworks.data.dto.category.CategoryDto
import com.example.tbcworks.data.dto.category.CategoryResponseDto
import retrofit2.http.GET

interface CategoryService {
    @GET("category")
    suspend fun getCategories(): CategoryResponseDto
}