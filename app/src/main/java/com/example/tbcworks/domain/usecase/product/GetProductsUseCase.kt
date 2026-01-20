package com.example.tbcworks.domain.usecase.product

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.Product
import com.example.tbcworks.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<Resource<List<Product>>> {
        return repository.getProducts()
    }
}