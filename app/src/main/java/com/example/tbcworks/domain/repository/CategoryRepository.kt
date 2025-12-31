package com.example.tbcworks.domain.repository

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategories(): Flow<Resource<List<Category>>>
}