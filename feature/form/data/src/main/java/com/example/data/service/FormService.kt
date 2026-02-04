package com.example.data.service

import com.example.data.dto.FieldDto
import retrofit2.http.GET

interface FormService {
    @GET(".")
    suspend fun getForm(): List<List<FieldDto>>
}