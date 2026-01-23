package com.example.composeapp.domain.usecase

import com.example.composeapp.domain.Resource
import com.example.composeapp.domain.model.Order
import com.example.composeapp.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetOrdersUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    operator fun invoke(): Flow<Resource<List<Order>>> {
        return repository.getOrders()
    }
}