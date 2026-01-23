package com.example.composeapp.domain.repository

import com.example.composeapp.domain.Resource
import com.example.composeapp.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrders(): Flow<Resource<List<Order>>>
}