package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.HandleResponse
import com.example.tbcworks.data.extension.asResource
import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.data.service.CategoryService
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.Category
import com.example.tbcworks.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val service: CategoryService,
    private val handleResponse: HandleResponse
) : CategoryRepository {

    override fun getCategories(): Flow<Resource<List<Category>>> {
        return handleResponse.safeApiCall {
            service.getCategories().categories
        }.map { resource ->
            val mapped = resource.asResource { list ->
                list.map { it.toDomain() }
            }
            println("CategoryRepositoryImpl getCategories: $mapped")

            mapped
        }
    }


}