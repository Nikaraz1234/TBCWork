package com.example.mycomposeapp.data.service

import com.example.mycomposeapp.data.dto.FieldDto
import retrofit2.http.GET

interface FormService {
    @GET(".")
    suspend fun getForm(): List<List<FieldDto>>
}