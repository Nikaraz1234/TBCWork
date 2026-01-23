package com.example.composeapp.data.service

import com.example.composeapp.data.dto.OrderDto
import retrofit2.http.GET

interface OrderService {
    @GET("orders")
    suspend fun getCategories(): List<OrderDto>
}