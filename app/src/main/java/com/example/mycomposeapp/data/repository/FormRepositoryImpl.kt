package com.example.mycomposeapp.data.repository

import com.example.mycomposeapp.data.common.HandleResponse
import com.example.mycomposeapp.data.extension.asResource
import com.example.mycomposeapp.data.mapper.toDomain
import com.example.mycomposeapp.data.service.FormService
import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.model.Form
import com.example.mycomposeapp.domain.repository.FormRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FormRepositoryImpl @Inject constructor(
    val service: FormService,
    val handleResponse: HandleResponse
) : FormRepository {
    override fun getForm(): Flow<Resource<Form>> {
        return handleResponse.safeApiCall {
            service.getForm()
        }.map { resource ->
            resource.asResource { dto ->
                dto.toDomain()
            }
        }
    }

}