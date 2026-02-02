package com.example.mycomposeapp.domain.repository

import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.model.Form
import kotlinx.coroutines.flow.Flow

interface FormRepository {
    fun getForm() : Flow<Resource<Form>>
}