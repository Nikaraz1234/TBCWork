package com.example.tbcworks.domain.usecase

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.Category
import com.example.tbcworks.domain.repository.CategoryRepository
import com.example.tbcworks.domain.service.CategoryFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFilteredCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository,
    private val categoryFilter: CategoryFilter
) {
    operator fun invoke(query: String): Flow<Resource<List<Category>>> {
        return repository.getCategories().map { resource ->
            when (resource) {
                is Resource.Success ->
                    Resource.Success(
                        categoryFilter.filter(resource.data, query)
                    )

                is Resource.Error -> resource
                Resource.Loading -> Resource.Loading
            }
        }
    }
}