package com.example.tbcworks.domain.usecase.category

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.Category
import com.example.tbcworks.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(): Flow<Resource<List<Category>>> {
        return repository.getCategories()
    }
}