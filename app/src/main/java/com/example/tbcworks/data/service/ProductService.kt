package com.example.tbcworks.data.service

import com.example.tbcworks.data.dto.product.ProductDto
import com.example.tbcworks.domain.Resource
import retrofit2.http.GET

interface ProductService {
    @GET("events")
    suspend fun getProducts(): List<ProductDto>
}