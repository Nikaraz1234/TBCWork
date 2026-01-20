package com.example.tbcworks.data.repository


import com.example.tbcworks.data.common.HandleResponse
import com.example.tbcworks.data.extension.asResource
import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.data.service.ProductService
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.Product
import com.example.tbcworks.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor(
    private val service: ProductService,
    private val handleResponse: HandleResponse
) : ProductRepository {

    override fun getProducts(): Flow<Resource<List<Product>>> {
        return handleResponse.safeApiCall {
            service.getProducts()
        }.map { resource ->
            resource.asResource { list -> list.map { it.toDomain() } }
        }
    }

}