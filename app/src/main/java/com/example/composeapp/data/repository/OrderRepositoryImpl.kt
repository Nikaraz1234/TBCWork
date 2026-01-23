package com.example.composeapp.data.repository

import com.example.composeapp.data.common.HandleResponse
import com.example.composeapp.data.extension.asResource
import com.example.composeapp.data.mapper.toDomain
import com.example.composeapp.data.service.OrderService
import com.example.composeapp.domain.Resource
import com.example.composeapp.domain.model.Order
import com.example.composeapp.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepositoryImpl @Inject constructor(
    private val service: OrderService,
    private val handleResponse: HandleResponse
) : OrderRepository {

    override fun getOrders(): Flow<Resource<List<Order>>> {
        return handleResponse.safeApiCall {
            service.getCategories()
        }.map { resource ->
            resource.asResource { list -> list.map { it.toDomain() } }
        }
    }
}