package com.example.domain.repository

import com.example.domain.Resource
import com.example.domain.model.Form
import kotlinx.coroutines.flow.Flow

interface FormRepository {
    fun getForm() : Flow<Resource<Form>>
}